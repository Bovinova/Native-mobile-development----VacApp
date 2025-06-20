package pe.edu.upc.vacapp.inventory.presentation.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.inventory.domain.model.InventoryImage
import pe.edu.upc.vacapp.inventory.presentation.viewmodel.InventoryViewModel
import pe.edu.upc.vacapp.ui.theme.Color

//@Preview(showBackground = true)
@Composable
fun InventoryCardList(
    viewmodel: InventoryViewModel,
    onTap: (Inventory) -> Unit
) {
    val inventories = viewmodel.inventories.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Inventories",
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            color = Color.ForestGreen,
            modifier = Modifier.padding(bottom = 20.dp, top = 15.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(inventories.value) {
                InventoryCard(it) {
                    onTap(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryCard(
    inventory: Inventory = Inventory(),
    onTap: () -> Unit = {}
) {
    Log.d("inventory", inventory.toString())
    val imgUrl = when (val image = inventory.image) {
        is InventoryImage.FromUrl -> image.url
        is InventoryImage.FromFile -> ""
        null -> ""
    }

    Card(
        modifier = Modifier
            .size(350.dp, 200.dp)
            .clickable { onTap() },
        colors = CardDefaults.cardColors(
            containerColor = Color.AlmondCream
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(170.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        inventory.name,
                        fontWeight = FontWeight.Light,
                        fontSize = 28.sp
                    )
                }

                AsyncImage(
                    model = imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(170.dp, 115.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                Column {
                    Text(
                        "Animal",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                    Text(
                        inventory.bovineId.toString(),
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp
                    )
                }

                Column {
                    Text(
                        "Weight",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                }

                Column {
                    Text(
                        "Vaccine Type",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                    Text(
                        inventory.vaccineType,
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}