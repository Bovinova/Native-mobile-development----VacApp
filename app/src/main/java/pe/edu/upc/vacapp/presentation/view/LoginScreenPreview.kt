import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.vacapp.R
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.TextFieldDefaults

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val backgroundColor = Color(0xFF4A5F58)
    val creamColor = Color(0xFFF2F2F2)
    val darkGreen = Color(0xFFFFFDD0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { -1000 }) + fadeIn()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vacapp_logo), // Usa tu imagen
                        contentDescription = "VacApp Logo",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 26.sp)

                Spacer(modifier = Modifier.height(24.dp))

                // Social login
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SocialButton("Gmail", R.drawable.ic_google)
                    SocialButton("Outlook", R.drawable.ic_outlook)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Email Field
                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    icon = Icons.Default.Email,
                    background = creamColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                CustomInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    icon = Icons.Default.VisibilityOff,
                    background = creamColor,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign In", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Forgot my password",
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    background: Color,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Black
            )
        },
        label = { Text(label, color = Color.Black) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(background, RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = background,
            focusedContainerColor = background,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

@Composable
fun SocialButton(label: String, iconRes: Int) {
    Row {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .height(45.dp)
                .weight(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF2CC))
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "$label Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = Color.Black)
        }
    }

}
