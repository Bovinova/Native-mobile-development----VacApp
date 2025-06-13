package pe.edu.upc.vacapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pe.edu.upc.vacapp.iam.presentation.navigation.Navigation

import pe.edu.upc.vacapp.ui.theme.VacAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VacAppTheme {
                Navigation()
            }
        }
    }
}

