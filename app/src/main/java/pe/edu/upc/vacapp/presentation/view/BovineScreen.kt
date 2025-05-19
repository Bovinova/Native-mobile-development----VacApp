package pe.edu.upc.vacapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import pe.edu.upc.vacapp.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


import pe.edu.upc.vacapp.domain.model.Bovine
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BovineScreen(
    viewModel: BovineViewModel,
    onTap: (Bovine) -> Unit
) {
    val bovines = viewModel.bovine.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(bovines) { bovine ->
            BovineListItemView(bovine = bovine, onTap = onTap)
        }
    }
}

@Composable
fun BovineListItemView(
    bovine: Bovine,
    onTap: (Bovine) -> Unit
) {
    val genderIconRes = when (bovine.gender.lowercase()) {
        "male" -> R.drawable.ic_masculino
        "female" -> R.drawable.ic_female
        else -> null
    }


    val (ageText, birthdayFormatted) = try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val birthDate = parser.parse(bovine.birthDate)
        if (birthDate != null) {
            val now = java.util.Calendar.getInstance().time
            val diff = now.time - birthDate.time
            val days = diff / (1000 * 60 * 60 * 24)
            val years = days / 365
            val months = (days % 365) / 30
            val remainingDays = (days % 365) % 30

            val age = buildString {
                if (years > 0) append("$years years ")
                if (months > 0) append("$months months ")
                if (remainingDays > 0 || (years == 0L && months == 0L)) append("$remainingDays days")
            }.trim()

            val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(birthDate)

            Pair(age, formatted)
        } else Pair("Edad desconocida", "Fecha desconocida")
    } catch (e: Exception) {
        Pair("Edad desconocida", "Fecha desconocida")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTap(bovine) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEFC8)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bovine.name,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                    modifier = Modifier.weight(1f)
                )
                genderIconRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "Gender",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = bovine.bovineImg,
                contentDescription = "Imagen de ${bovine.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))


            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                // Fila 1: Raza y cumpleaños
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Breed", style = MaterialTheme.typography.labelLarge)
                        Text(bovine.breed, style = MaterialTheme.typography.bodyMedium)
                    }
                    Column {
                        Text("Birth", style = MaterialTheme.typography.labelLarge)
                        Text(birthdayFormatted, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // Fila 2: Edad y ubicación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Age", style = MaterialTheme.typography.labelLarge)
                        Text(ageText, style = MaterialTheme.typography.bodyMedium)
                    }
                    Column {
                        Text("Location", style = MaterialTheme.typography.labelLarge)
                        Text(bovine.location, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // Fila 3: Establo (etiqueta)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(50),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7CCC8)), // Marrón claro
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Text(
                            text = "Stable #${bovine.stableId}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF4E342E)),
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}


