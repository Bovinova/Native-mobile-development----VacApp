package pe.edu.upc.vacapp.campaign.presentation.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.campaign.presentation.viewmodel.CampaignViewModel
import pe.edu.upc.vacapp.ui.theme.Color

@Composable

fun CampaignView(
    viewModel: CampaignViewModel
) {
    val campaigns = viewModel.campaigns.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
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

        LazyColumn {
            items(campaigns.value) {
                CardCampaignView(it)
            }
        }

    }
}

