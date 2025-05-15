package pe.edu.upc.vacapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upc.vacapp.domain.model.Bovine
import pe.edu.upc.vacapp.presentation.viewmodel.BovineViewModel

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTap(bovine) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${bovine.name}")
            Text(text = "Raza: ${bovine.breed}")
            Text(text = "Género: ${bovine.gender}")
            Text(text = "Ubicación: ${bovine.stableId}")
        }
    }
}

