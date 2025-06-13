package pe.edu.upc.vacapp.home.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.home.presentation.view.HomeView
import pe.edu.upc.vacapp.ui.theme.Color


@Preview
@Composable
fun Navigation() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        scrimColor = Color.Transparent,
        drawerContent = { DrawerList() },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopBarHome(
                    openmenu = {
                        scope.launch {
                            drawerState.open()
                        }
                    })
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
}

@Preview
@Composable
fun DrawerList() {
    Column(
        modifier = Modifier
            .padding(top = 45.dp)
            .background(Color.Green)
            .fillMaxHeight()
            .width(185.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.house),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)

                )
                Text(
                    "Home", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.cow),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    "Animal", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.megaphone),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    "Campaign", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.resource_package),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp),
                )
                Text(
                    "Inventory", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.barn),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    "Barn", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.gear),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)

                )
                Text(
                    "Settings", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.sign_out),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    "Exit", fontWeight = FontWeight.Normal,
                    fontSize = 20.sp, color = Color.White
                )
            }
        }
    }


}

@Preview
@Composable
fun TopBarHome(
    openmenu: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .height(50.dp)
    )
    {
        IconButton(onClick = { openmenu() }) {
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
}