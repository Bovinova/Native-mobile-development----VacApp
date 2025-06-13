package pe.edu.upc.vacapp.campaign.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.ui.theme.Color

@Composable
@Preview
fun AddCampaignView() {
    Card(
        modifier = Modifier
            .width(356.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.AlmondCream,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                "Name",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp
            )
            UnderlinedText("Healthy Herd 2025")
            Text(
                "Description",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp
            )
            UnderlinedText("Cattle vaccination")
            Text(
                "Start date",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp
            )
            UnderlinedText("09/05/2025")
            Text(
                "End date",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp,
            )
            UnderlinedText("09/05/2025")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painterResource(R.drawable.x_circle),
                        null,
                        modifier = Modifier.size(45.dp)
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painterResource(R.drawable.check_circle), null,
                        modifier = Modifier.size(45.dp)
                    )
                }
            }


        }
    }
}

@Composable
fun UnderlinedText(value: String) {
    Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    )
}