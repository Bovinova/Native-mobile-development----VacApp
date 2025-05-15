package pe.edu.upc.vacapp.presentation.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.presentation.di.PresentationModule
import pe.edu.upc.vacapp.presentation.view.BovineScreen
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel


// Colors
val DarkGreen = Color(0xFF4A5F58)
val BackgroundLight = Color(0xFFF2F2F2)
val CreamColor = Color(0xFFFFFDD0)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Home() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        NavigationItem(
            iconPainter = painterResource(id= R.drawable.ic_home),
            title = "Home",
            route = "home"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_organization),
            title = "Organization",
            route = "organization"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_cow),
            title = "Cows",
            route = "cows"
        ),
        NavigationItem(
            iconPainter = painterResource(id =R.drawable.ic_statistics),
            title = "Statistics",
            route = "statistics"
        )
    )

    val selectedIndex = remember { mutableStateOf(0) }
    val bovineViewModel = PresentationModule.getBovineViewModel()

    Scaffold(
        topBar = {
            var expanded by remember { mutableStateOf(false) }
            var rotated by remember { mutableStateOf(false) }
            val rotation by animateFloatAsState(targetValue = if (rotated) 180f else 0f)

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
                                text = { Text("Cerrar Sesion") },
                                onClick = { /* Acción de opción 2 */ }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = DarkGreen) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex.value == index,
                        icon = {
                            if (item.iconPainter != null) {
                                Image(
                                    painter = item.iconPainter,
                                    contentDescription = item.title ,
                                    modifier = Modifier.size(45.dp)
                                )
                            } else if (item.icon != null) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = Color.Black,
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        },
                        onClick = {
                            selectedIndex.value = index
                            navController.navigate(item.route)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = CreamColor
                        )
                    )
                }
            }
        },
        containerColor = BackgroundLight
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                Text(
                    text = "Welcome to VacApp!",
                    //text = "Welcome, $userName!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }

            composable("organization") {

            }
            composable("cows") {
                BovineScreen(
                    viewModel = bovineViewModel,
                    onTap = { /* pondre mas info para editar */ }
                )
            }
            composable("statistics") {

            }
        }
    }
}

data class NavigationItem(
    val icon: ImageVector? = null,
    val iconPainter: Painter? = null,
    val title: String,
    val route: String
)








