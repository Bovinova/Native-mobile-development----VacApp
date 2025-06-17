package pe.edu.upc.vacapp.animal.presentation.view

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import pe.edu.upc.vacapp.R
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.animal.domain.model.AnimalImage
import pe.edu.upc.vacapp.animal.presentation.viewmodel.AnimalViewModel
import pe.edu.upc.vacapp.ui.theme.Color
import java.io.File
import java.util.Calendar

//@Preview(showBackground = true)
@Composable
fun AddAnimalForm(
    viewmodel: AnimalViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Add animal",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        AddAnimalCard(viewmodel)
    }
}

//@Preview
@Composable
fun AddAnimalCard(
    viewmodel: AnimalViewModel
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imageFile = remember { mutableStateOf<File?>(null) }
    val newAnimal = remember { mutableStateOf(Animal()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri

        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val tempFile = File.createTempFile("animal", ".jpg", context.cacheDir)
            inputStream?.use { input -> tempFile.outputStream().use { input.copyTo(it) } }
            imageFile.value = tempFile
            newAnimal.value = newAnimal.value.copy(image = AnimalImage.FromFile(tempFile))
        }
    }

    val icon = if (newAnimal.value.isMale) R.drawable.gender_male else R.drawable.gender_female

    Card(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.background(Color.AlmondCream),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                fallback = painterResource(R.drawable.add_image_placeholder),
                model = imageUri.value,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .size(300.dp, 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        launcher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )

            TextField(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .widthIn(min = 155.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            newAnimal.value = newAnimal.value.copy(isMale = !newAnimal.value.isMale)
                        }
                    ) {
                        Icon(
                            painterResource(icon),
                            null,
                            tint = Color.Black
                        )
                    }
                },
                label = { Text("Name") },
                value = newAnimal.value.name,
                onValueChange = { newAnimal.value = newAnimal.value.copy(name = it) }
            )

            Column(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        ),
                        value = newAnimal.value.breed,
                        label = { Text("Breed") },
                        onValueChange = { newAnimal.value = newAnimal.value.copy(breed = it) }
                    )

                    TextField(
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        ),
                        value = newAnimal.value.weight.toString(),
                        label = { Text("Weight") },
                        onValueChange = { newWeightString ->
                            newAnimal.value = newAnimal.value.copy(
                                weight = newWeightString.toDoubleOrNull() ?: newAnimal.value.weight
                            )
                        }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    DatePickerTextField(
                        label = "Birthdate",
                        date = newAnimal.value.birthDate,
                        onDateChange = {
                            newAnimal.value = newAnimal.value.copy(birthDate = it)
                        }
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        ),
                        value = newAnimal.value.barnId.toString(),
                        label = { Text("Barn") },
                        onValueChange = { newBarnIdString ->
                            newAnimal.value = newAnimal.value.copy(
                                barnId = newBarnIdString.toIntOrNull() ?: newAnimal.value.barnId
                            )
                        }
                    )
                    TextField(
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        ),
                        value = newAnimal.value.location,
                        label = { Text("Location") },
                        onValueChange = { newAnimal.value = newAnimal.value.copy(location = it) }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painterResource(R.drawable.x_circle),
                        null,
                        modifier = Modifier.size(45.dp)
                    )
                }
                IconButton(
                    onClick = { viewmodel.addAnimal(newAnimal.value) }
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

@Composable
fun NumberTextField(
    label: String,
    initialValue: Number?,
    onValueChange: (Number) -> Unit,
    modifier: Modifier = Modifier
) {
    val textState = remember { mutableStateOf(initialValue?.toString() ?: "") }

    TextField(
        value = textState.value,
        onValueChange = { newText ->
            textState.value = newText

            // Parse y callback
            val parsedValue = when (initialValue) {
                is Int -> newText.toIntOrNull()
                is Double -> newText.toDoubleOrNull()
                else -> null
            }
            parsedValue?.let { onValueChange(it) }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}


@Composable
fun DatePickerTextField(
    label: String,
    date: String,
    onDateChange: (String) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                onDateChange(selectedDate.format(formatter))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = date,
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Pick Date"
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}
