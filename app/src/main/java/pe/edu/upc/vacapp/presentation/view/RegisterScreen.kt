package pe.edu.upc.vacapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    val error by viewModel.error.collectAsState()
    val user by viewModel.user.collectAsState()

    // Validaciones individuales
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasMinLength = password.length >= 8

    val allValid = hasUpperCase && hasLowerCase && hasDigit && hasMinLength

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.vacapp_logo),
            contentDescription = "VacApp Logo",
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(300.dp)
                .padding(bottom = 1.dp)
        )

        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium,
            color = DarkGreen,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 1.dp),
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", color = DarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CreamColor,
                focusedContainerColor = CreamColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = DarkGreen,
                cursorColor = DarkGreen,
                focusedTextColor = DarkGreen,
                unfocusedTextColor = DarkGreen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = DarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CreamColor,
                focusedContainerColor = CreamColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = DarkGreen,
                cursorColor = DarkGreen,
                focusedTextColor = DarkGreen,
                unfocusedTextColor = DarkGreen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = password,
            onValueChange = {
                password = it
                localError = null
            },
            label = { Text("Password", color = DarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña")
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CreamColor,
                focusedContainerColor = CreamColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = DarkGreen,
                cursorColor = DarkGreen,
                focusedTextColor = DarkGreen,
                unfocusedTextColor = DarkGreen
            )
        )

        Spacer(modifier = Modifier.height(8.dp))


        if (password.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tu contraseña debe contener:", color = DarkGreen, fontSize = 14.sp)

            Column(modifier = Modifier.fillMaxWidth()) {
                PasswordRequirementItem("Al menos 8 caracteres", hasMinLength)
                PasswordRequirementItem("Una letra mayúscula", hasUpperCase)
                PasswordRequirementItem("Una letra minúscula", hasLowerCase)
                PasswordRequirementItem("Un número", hasDigit)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (allValid) {
                    viewModel.register(name, email, password)
                } else {
                    localError = "La contraseña no cumple con todos los requisitos."
                }
            },
            modifier = Modifier
                .width(220.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGreen,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Sign in", color = DarkGreen)
        }

        localError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }

    if (user != null) {
        onRegisterSuccess()
    }
}

@Composable
fun PasswordRequirementItem(text: String, satisfied: Boolean) {
    val icon = if (satisfied) "✅" else "❌"
    val color = if (satisfied) Color(0xFF388E3C) else MaterialTheme.colorScheme.error
    Text(
        text = "$icon $text",
        color = color,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
    )
}
