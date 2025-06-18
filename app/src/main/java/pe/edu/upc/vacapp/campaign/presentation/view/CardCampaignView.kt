package pe.edu.upc.vacapp.campaign.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.campaign.domain.model.Campaign
import pe.edu.upc.vacapp.ui.theme.Color

@Composable
@Preview
fun CardCampaignView(
    campaign: Campaign = Campaign()
) {
    Card(
        modifier = Modifier
            .width(380.dp)
            .height(195.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.AlmondCream,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding interior para dar margen al contenido
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                campaign.name,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Description:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
                Text(
                    campaign.description,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Start Date:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
                Text(
                    campaign.startdate,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "End Date:",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
                Text(
                    campaign.enddate,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            }
        }
    }
}