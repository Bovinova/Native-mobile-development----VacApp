package pe.edu.upc.vacapp.presentation.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.vacapp.presentation.di.PresentationModule
import pe.edu.upc.vacapp.presentation.view.LoginScreen
import pe.edu.upc.vacapp.presentation.view.RegisterScreen


@Composable
fun Home() {
    val navController = rememberNavController()

    val bovineViewModel = PresentationModule.getBovineViewModel()
    val authViewModel = PresentationModule.getAuthViewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // LOGIN
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { navController.navigate("main") { popUpTo("login") { inclusive = true } } },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // REGISTER
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = { navController.navigate("main") { popUpTo("register") { inclusive = true } } },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        // MAIN APP
        composable("main") {
            MainScaffold(navController, bovineViewModel)
        }
    }
}
