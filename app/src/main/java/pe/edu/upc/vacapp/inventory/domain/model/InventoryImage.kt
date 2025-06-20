package pe.edu.upc.vacapp.inventory.domain.model

import java.io.File

sealed class InventoryImage {
    data class FromFile(val file: File) : InventoryImage()
    data class FromUrl(val url: String) : InventoryImage()
}
