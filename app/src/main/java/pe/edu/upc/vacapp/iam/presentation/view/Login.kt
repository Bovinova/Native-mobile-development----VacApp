package pe.edu.upc.vacapp.iam.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.iam.presentation.viewmodel.AuthViewModel
import pe.edu.upc.vacapp.ui.theme.Color

//@Preview
@Composable
fun Login(
    viewmodel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    goToRegister: () -> Unit
) {
    val user = viewmodel.user.collectAsState()
    val loginSuccess = viewmodel.loginSuccess.collectAsState()
    val showPassword = remember { mutableStateOf(false) }

    LaunchedEffect(loginSuccess.value) {
        if (loginSuccess.value == true) {
            onLoginSuccess()
            viewmodel.resetLoginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = R.drawable.vacapp_logo,
                contentDescription = null,
                modifier = Modifier
                    .height(298.dp)
                    .width(298.dp)
                    .clip(RoundedCornerShape(360.dp)),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            "Sign In",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.AlmondCream
                ),
                onClick = {}) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google),
                        null,
                        tint = androidx.compose.ui.graphics.Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        "Gmail", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                }
            }
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.AlmondCream
                ),
                onClick = {}) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.outlook),
                        null,
                        tint = androidx.compose.ui.graphics.Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        "Outlook",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            TextField(
                modifier = Modifier
                    .width(375.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedContainerColor = Color.AlmondCream,
                    unfocusedContainerColor = Color.AlmondCream
                ),
                value = user.value.email,
                onValueChange = { viewmodel.updateEmail(it) },
                placeholder = {
                    Text(
                        "Email", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.envelope_simple),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )

            TextField(
                modifier = Modifier
                    .width(375.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedContainerColor = Color.AlmondCream,
                    unfocusedContainerColor = Color.AlmondCream
                ),
                value = user.value.password,
                onValueChange = { viewmodel.updatePassword(it) },
                placeholder = {
                    Text(
                        "Password",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPassword.value = !showPassword.value
                        }
                    ) {
                        val icon = if (showPassword.value) R.drawable.eye_slash else R.drawable.eye
                        Icon(
                            painterResource(icon), null,
                            tint = Color.Black
                        )
                    }

                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.lock_key),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                visualTransformation = if (showPassword.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(215.dp)
                    .height(65.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.ForestGreen
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = { viewmodel.login() }
            ) {
                Text(
                    "Sign In",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

            TextButton(onClick = {
                viewmodel.clearUser()
                goToRegister()
            }) {
                Text(
                    "Create account",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
            }
        }
    }
}