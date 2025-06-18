package pe.edu.upc.vacapp.campaign.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.campaign.presentation.viewmodel.CampaignViewModel

import pe.edu.upc.vacapp.ui.theme.Color

@Composable

fun FormCampaignView(goHome: ()-> Unit={}, viewModel: CampaignViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Add Campaign",
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
       AddCampaignView(goHome = {goHome()}, viewModel =viewModel  )

    }
}