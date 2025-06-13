package pe.edu.upc.vacapp.campaign.presentation.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import pe.edu.upc.vacapp.ui.theme.Color

@Composable
@Preview
fun CampaignView() {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "My Campaigns",
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        CardCampaignView()
        CardCampaignView()
        CardCampaignView()

    }
}

