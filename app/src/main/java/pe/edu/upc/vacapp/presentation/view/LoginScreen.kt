package pe.edu.upc.vacapp.presentation.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import pe.edu.upc.vacapp.presentation.viewmodel.AuthViewModel
import pe.edu.upc.vacapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import android.app.AlertDialog
import android.view.Gravity
import android.widget.TextView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.VisualTransformation


val DarkGreen = Color(0xFF4A5F58)
val BackgroundLight = Color(0xFFF2F2F2)
val CreamColor = Color(0xFFFFFDD0)

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by viewModel.error.collectAsState()
    val user by viewModel.user.collectAsState()
    val context = LocalContext.current


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
            text = "Sign In",
            style = MaterialTheme.typography.headlineMedium,
            color = DarkGreen,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 1.dp),
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val azulGoogleInt = Color(0xFF1A73E8).toArgb()

                    val dialogGoogle = AlertDialog.Builder(context)
                        .setTitle("¡Coming soon!")
                        .setMessage("We're working to enable Google sign-in 🔐✨\nThank you for your patience.")
                        .setIcon(R.drawable.ic_google)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialogGoogle.setOnShowListener {
                        dialogGoogle.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(azulGoogleInt)
                        val messageView = dialogGoogle.findViewById<TextView>(android.R.id.message)
                        messageView?.gravity = Gravity.CENTER
                        messageView?.textSize = 16f
                    }

                    dialogGoogle.show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text("Google", color = DarkGreen)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {
                    val azulOutlookInt = Color(0xFF0078D4).toArgb()

                    val dialogOutlook = AlertDialog.Builder(context)
                        .setTitle("¡Coming soon!")
                        .setMessage("We're working to enable Outlook sign-in 📧✨\nThank you for your patience.")
                        .setIcon(R.drawable.ic_outlook)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialogOutlook.setOnShowListener {
                        dialogOutlook.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(azulOutlookInt)

                        val messageView = dialogOutlook.findViewById<TextView>(android.R.id.message)
                        messageView?.gravity = Gravity.CENTER
                        messageView?.textSize = 16f
                    }

                    dialogOutlook.show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlook),
                    contentDescription = "Outlook",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text("Outlook", color = DarkGreen)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
            onValueChange = { password = it },
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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.login(
                    email,
                    password,
                    onSuccess = { onLoginSuccess() },
                    onFailure = { /* puedes mostrar un snackbar, toast o mensaje si deseas */ }
                )
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
            Text("Sign In")
        }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Sign up", color = DarkGreen)
        }

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }

}