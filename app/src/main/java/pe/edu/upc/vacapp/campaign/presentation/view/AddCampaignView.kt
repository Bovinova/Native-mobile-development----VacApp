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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.campaign.domain.model.Campaign
import pe.edu.upc.vacapp.campaign.presentation.viewmodel.CampaignViewModel
import pe.edu.upc.vacapp.ui.theme.Color

@Composable

fun AddCampaignView(
    goHome: () -> Unit = {},
    viewModel: CampaignViewModel
) {

    val campaign = remember { mutableStateOf(Campaign()) }
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

            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
                value = campaign.value.name,
                onValueChange = {
                    campaign.value = campaign.value.copy(name = it)
                },

                label = {
                    Text(
                        "Name",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }

            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
                value = campaign.value.description,
                onValueChange = {
                    campaign.value = campaign.value.copy(description = it)
                },

                label = {
                    Text(
                        "Description",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }

            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
                value = campaign.value.startdate,
                onValueChange = {
                    campaign.value = campaign.value.copy(startdate = it)
                },

                label = {
                    Text(
                        "Start date",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }

            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
                value = campaign.value.enddate,
                onValueChange = {
                    campaign.value = campaign.value.copy(enddate = it)
                },

                label = {
                    Text(
                        "End date",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }

            )




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { goHome() }
                ) {
                    Icon(
                        painterResource(R.drawable.x_circle),
                        null,
                        modifier = Modifier.size(45.dp)
                    )
                }
                IconButton(
                    onClick = { viewModel.addCanpaing(campaign.value) }
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

