package pe.edu.upc.vacapp.home.presentation.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.ui.theme.Color

@Composable
@Preview
fun HomeView() {
    val isButtonActive = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Welcome",
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = androidx.compose.ui.graphics.Color(0xFF1D3620),
                textAlign = TextAlign.Center
            )
            Text(
                "Juan Jose!",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 45.sp,
                color = androidx.compose.ui.graphics.Color(0xFF1D3620),
                textAlign = TextAlign.Center
            )
        }

        Card(
            modifier = Modifier
                .width(365.dp)
                .height(120.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.AlmondCream,
                contentColor = Color.Black
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Registered animals", fontWeight = FontWeight.Light, fontSize = 32.sp)

                Text("123", fontWeight = FontWeight.Bold, fontSize = 48.sp)
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Card(
                    modifier = Modifier
                        .width(165.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.AlmondCream,
                        contentColor = Color.Black,
                    ),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("4", fontWeight = FontWeight.Bold, fontSize = 64.sp)
                        Text("Campaigns", fontWeight = FontWeight.Light, fontSize = 20.sp)
                    }
                }
                Card(
                    modifier = Modifier
                        .width(165.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.AlmondCream,
                        contentColor = Color.Black
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    )
                    {
                        Text("32", fontWeight = FontWeight.Bold, fontSize = 64.sp)
                        Text("Barn", fontWeight = FontWeight.Light, fontSize = 20.sp)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Card(
                    modifier = Modifier
                        .width(165.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.AlmondCream,
                        contentColor = Color.Black
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("10", fontWeight = FontWeight.Bold, fontSize = 64.sp)
                        Text(
                            "Vaccines about to expire",
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .width(165.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.AlmondCream,
                        contentColor = Color.Black
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    )
                    {
                        Text("32", fontWeight = FontWeight.Bold, fontSize = 64.sp)
                        Text("Products", fontWeight = FontWeight.Light, fontSize = 20.sp)
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(25.dp, 0.dp)
        ) {
            Text(
                "Upcoming Events", fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = androidx.compose.ui.graphics.Color(0xFF1D3620),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp), // Espacio para el Divider
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "Foot and Mouth Disease Vaccination", fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                    Text(
                        "10-May", fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    color = androidx.compose.ui.graphics.Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 0.15.dp) // Margen izquierdo/derecho para la línea
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp), // Espacio para el Divider
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "Internal and External Deworming", fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                    Text(
                        "05-July", fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    color = androidx.compose.ui.graphics.Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 0.15.dp) // Margen izquierdo/derecho para la línea
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp), // Espacio para el Divider
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "Brucellosis Sanitation Campaign", fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                    Text(
                        "23-August", fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    color = androidx.compose.ui.graphics.Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 0.15.dp) // Margen izquierdo/derecho para la línea
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier.background(Color.ForestGreen).width(100.dp),
            ) {
                Card(modifier = Modifier.align(Alignment.TopStart)) { Text("qweqwe") }

                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                    isButtonActive.value = !isButtonActive.value
                }) {
                    val icon =
                        if (isButtonActive.value) R.drawable.x_circle else R.drawable.plus_circle

                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                }
            }
        }
    }

}
