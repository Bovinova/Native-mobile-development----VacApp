package pe.edu.upc.vacapp.iam.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.vacapp.iam.presentation.di.PresentationModule
import pe.edu.upc.vacapp.iam.presentation.view.Login
import pe.edu.upc.vacapp.iam.presentation.view.Register
import pe.edu.upc.vacapp.ui.theme.Color

@Preview
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val authViewModel = PresentationModule.getAuthViewModel()
    LaunchedEffect(Unit) {
        authViewModel.verifyLogIn()
    }
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    Scaffold(
        modifier = Modifier.background(Color.LightGray)
    ) { padding ->
        NavHost(
            navController,
            startDestination = if (isLoggedIn.value) "home" else "login",
            modifier = Modifier.padding(padding)
        ) {
            composable("login") {
                Login(authViewModel) {
                    navController.navigate("register")
                }
            }

            composable("register") {
                Register(authViewModel) {
                    navController.navigate("login")
                }
            }

            composable("home") {
                Button(onClick = { authViewModel.logout() }) { }
                Text("Home Screen") // Placeholder for home screen
            }
        }
    }
}