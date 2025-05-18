package pe.edu.upc.vacapp.presentation.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.presentation.di.PresentationModule
import pe.edu.upc.vacapp.presentation.view.BovineScreen
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import pe.edu.upc.vacapp.presentation.view.LoginScreen
import pe.edu.upc.vacapp.presentation.viewmodel.AuthViewModel

import androidx.compose.runtime.collectAsState

// Colors
val DarkGreen = Color(0xFF4A5F58)
val BackgroundLight = Color(0xFFF2F2F2)
val CreamColor = Color(0xFFFFFDD0)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    rootNavController: NavHostController, bovineViewModel: BovineViewModel, authViewModel: AuthViewModel) {
    val internalNavController = rememberNavController()


    val navigationItems = listOf(
        NavigationItem(
            iconPainter = painterResource(id= R.drawable.ic_home),
            title = "Home",
            route = "home"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_campaigns),
            title = "Campaigns",
            route = "campaigns"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_cow),
            title = "Animals",
            route = "animals"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_inventory),
            title = "Inventory",
            route = "inventory"
        )
    )

    val selectedIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            var expanded by remember { mutableStateOf(false) }
            var rotated by remember { mutableStateOf(false) }
            val rotation by animateFloatAsState(targetValue = if (rotated) 180f else 0f)
            var showDialog by remember { mutableStateOf(false) }

            TopAppBar(
                title = {
                    Text(
                        "VacApp",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Row {
                        IconButton(
                            onClick = {
                                rotated = !rotated
                                expanded = !expanded
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.rotate(rotation)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                                rotated = false
                            }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Mi cuenta") },
                                onClick = { /* Acción de opción 1 */ }
                            )
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_log_out),
                                            contentDescription = "Cerrar Sesión",
                                            tint = DarkGreen
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Cerrar Sesión")
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    rotated = false
                                    authViewModel.logout()
                                    rootNavController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )


                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
            )
        },
        containerColor = BackgroundLight
    ) { padding ->
        NavHost(
            navController = internalNavController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to VacApp!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DashboardButton("Campaigns", R.drawable.ic_campaigns, {}, modifier = Modifier.weight(1f))
                        DashboardButton("Animals", R.drawable.ic_cow, {}, modifier = Modifier.weight(1f))
                        DashboardButton("Inventory", R.drawable.ic_inventory, {}, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardButton(
    title: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(100.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CreamColor)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = DarkGreen,
                modifier = Modifier.size(40.dp)
            )
            Text(title, color = DarkGreen)
        }
    }
}


data class NavigationItem(
    val icon: ImageVector? = null,
    val iconPainter: Painter? = null,
    val title: String,
    val route: String
){}