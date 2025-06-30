package pe.edu.upc.vacapp.inventory.presentation.view

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.inventory.domain.model.InventoryImage
import pe.edu.upc.vacapp.inventory.presentation.viewmodel.InventoryViewModel
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.ui.theme.Color
import java.io.File
import java.util.Calendar
import androidx.compose.ui.text.TextStyle


//@Preview(showBackground = true)
@Composable
fun AddInventoryForm(
    viewmodel: InventoryViewModel,
    goHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Add inventory",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 40.dp),
            color = Color.Black,
        )

        AddInventoryCard(viewmodel, goHome = { goHome() })
    }
}

//@Preview
@Composable
fun AddInventoryCard(
    viewmodel: InventoryViewModel,
    goHome: () -> Unit
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imageFile = remember { mutableStateOf<File?>(null) }
    val newInventory = remember { mutableStateOf(Inventory()) }
    val animals = viewmodel.animal.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri

        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val tempFile = File.createTempFile("inventory", ".jpg", context.cacheDir)
            inputStream?.use { input -> tempFile.outputStream().use { input.copyTo(it) } }
            imageFile.value = tempFile
            newInventory.value = newInventory.value.copy(image = InventoryImage.FromFile(tempFile))
        }
    }

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
                label = { Text("Name") },
                value = newInventory.value.name,
                onValueChange = { newInventory.value = newInventory.value.copy(name = it) },
                textStyle = TextStyle(color = Color.Black)
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
                        value = newInventory.value.vaccineType,
                        label = { Text("Vaccine Type") },
                        onValueChange = { newInventory.value = newInventory.value.copy(vaccineType = it) },
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    DatePickerTextField(
                        label = "Vaccine Date",
                        date = newInventory.value.vaccineDate,
                        onDateChange = {
                            newInventory.value = newInventory.value.copy(vaccineDate = it)
                        },
                        textStyle = TextStyle(color = Color.Black)
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        DropdownSelector(
                            label = "Animal",
                            items = animals.value,
                            onItemSelected = { animal ->
                                newInventory.value = newInventory.value.copy(bovineId = animal.id)
                            },
                            textStyle = TextStyle(color = Color.Black)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { goHome() }
                ) {
                    Icon(
                        painterResource(R.drawable.x_circle),
                        null,
                        modifier = Modifier.size(45.dp),
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { viewmodel.addInventory(newInventory.value) }
                ) {
                    Icon(
                        painterResource(R.drawable.check_circle), null,
                        modifier = Modifier.size(45.dp),
                        tint = Color.Black
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
    onDateChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]")
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
                    contentDescription = "Pick Date",
                    tint = Color.Black,
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        textStyle = textStyle
    )
}


@Composable
fun DropdownSelector(
    label: String,
    items: List<Animal>,
    onItemSelected: (Animal) -> Unit,
    textStyle: TextStyle = TextStyle.Default
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<Animal?>(null) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedItem.value?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded.value = true },
                    tint = Color.Black
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            textStyle = textStyle
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem.value = item
                        expanded.value = false
                        onItemSelected(selectedItem.value!!)
                    },
                    text = { Text(text = item.name) }
                )
            }
        }
    }
}