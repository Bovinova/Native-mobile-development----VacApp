package pe.edu.upc.vacapp.presentation.navigation


import androidx.compose.runtime.Composable

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.vacapp.presentation.di.PresentationModule
import pe.edu.upc.vacapp.presentation.view.LoginScreen
import pe.edu.upc.vacapp.presentation.view.RegisterScreen

@Composable
fun Home() {
    val rootNavController = rememberNavController()
    val bovineViewModel = PresentationModule.getBovineViewModel()
    val authViewModel = PresentationModule.getAuthViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()


    NavHost(
        navController = rootNavController,
        startDestination = if (isLoggedIn) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    rootNavController.navigate("main")
                },
                onNavigateToRegister = { rootNavController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    rootNavController.navigate("main") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = { rootNavController.navigate("login") }
            )
        }
        composable("main") {
            MainScaffold(rootNavController, bovineViewModel, authViewModel)
        }
    }
}
