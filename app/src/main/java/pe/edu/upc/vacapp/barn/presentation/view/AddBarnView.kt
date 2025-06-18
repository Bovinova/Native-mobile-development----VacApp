package pe.edu.upc.vacapp.barn.presentation.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.barn.presentation.viewmodel.BarnViewModel
import pe.edu.upc.vacapp.ui.theme.Color

@Composable

fun AddBarnView(
    viewModel: BarnViewModel,
    goHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Add Barn",
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        FormBarnView(viewModel, goHome = { goHome() })

    }
}
