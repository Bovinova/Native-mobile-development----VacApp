package pe.edu.upc.vacapp.home.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.home.presentation.view.HomeView
import pe.edu.upc.vacapp.ui.theme.Color

@Preview
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green).height(50.dp)
            )
            {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.list),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp),
                        tint = Color.LightGray
                    )
                }
            }
        },
        containerColor = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        NavHost(
            navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeView()
            }
        }


    }
}