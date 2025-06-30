package pe.edu.upc.vacapp.barn.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.barn.domain.model.Barn
import pe.edu.upc.vacapp.ui.theme.Color

@Composable
@Preview
fun BarnCardView(
    barn: Barn = Barn(limit = "65", name = "wdfghjkl")
) {
    Card(
        modifier = Modifier
            .width(380.dp)
            .height(210.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.AlmondCream,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp), // Padding interior para dar margen al contenido
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                barn.name,
                fontWeight = FontWeight.Normal,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    45.dp,
                    Alignment.CenterHorizontally
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Capacity:",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        "${barn.limit} Bovines",
                        fontWeight = FontWeight.Light,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }
            }
        }

    }
}