package pe.edu.upc.vacapp.iam.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
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

    val loginViewModel = PresentationModule.getLoginViewModel()

    Scaffold(
        modifier = Modifier.background(Color.LightGray)
    ) { padding ->
        NavHost(
            navController,
            startDestination = "login",
            modifier = Modifier.padding(padding)
        ) {
            composable("login") {
                Login(loginViewModel) {
                    navController.navigate("register")
                }
            }

            composable("register") {
                Register(loginViewModel) {
                    navController.navigate("login")
                }
            }
        }
    }
}