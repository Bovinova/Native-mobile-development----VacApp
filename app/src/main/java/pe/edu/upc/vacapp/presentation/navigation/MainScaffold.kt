package pe.edu.upc.vacapp.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.presentation.view.BovineScreen
import pe.edu.upc.vacapp.presentation.viewmodel.AuthViewModel
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel

val DarkGreen = Color(0xFF4A5F58)
val BackgroundLight = Color(0xFFF2F2F2)
val CreamColor = Color(0xFFFFFDD0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    rootNavController: NavHostController,
    bovineViewModel: BovineViewModel,
    authViewModel: AuthViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val internalNavController = rememberNavController()
    val selectedIndex = remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkGreen,
                modifier = Modifier.width(250.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                DrawerItem("Home", R.drawable.ic_home) {
                    selectedIndex.value = 0
                    internalNavController.navigate("home") {
                        popUpTo(internalNavController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Campaigns", R.drawable.ic_campaigns) {
                    selectedIndex.value = 1
                    internalNavController.navigate("campaigns")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Animals", R.drawable.ic_cow) {
                    selectedIndex.value = 2
                    internalNavController.navigate("animals")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Inventory", R.drawable.ic_inventory) {
                    selectedIndex.value = 3
                    internalNavController.navigate("inventory")
                    scope.launch { drawerState.close() }
                }

                Spacer(modifier = Modifier.weight(1f))

                DrawerItem("Settings", R.drawable.ic_settings) {
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Log out", R.drawable.ic_log_out) {
                    authViewModel.logout()
                    rootNavController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "VacApp",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
                )
            }
        ) { padding ->
            NavHost(
                navController = internalNavController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") {
                    val user by authViewModel.user.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome a VacAPP",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        user?.let {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = CreamColor)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Username: ${it.username}", color = DarkGreen)
                                    Text("Email: ${it.email}", color = DarkGreen)
                                    Text("Token: ${it.token}", color = DarkGreen)
                                }
                            }
                        }
                    }
                }
                composable("campaigns") {

                }
                composable("animals") {
                    val user by authViewModel.user.collectAsState()
                    val token = user?.token ?: ""

                    LaunchedEffect(token) {
                        if (token.isNotEmpty()) {
                            bovineViewModel.loadBovines(token)
                        }
                    }

                    BovineScreen(
                        viewModel = bovineViewModel,
                        onTap = {  }
                    )
                }
                composable("inventory") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Inventory Screen")
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(title: String, iconRes: Int, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, color = Color.White, fontSize = 16.sp)
            }
        },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    )
}
