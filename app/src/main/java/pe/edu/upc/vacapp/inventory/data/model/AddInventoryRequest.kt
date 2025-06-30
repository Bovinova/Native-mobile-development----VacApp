package pe.edu.upc.vacapp.inventory.data.model

import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.inventory.domain.model.InventoryImage
import java.io.File
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

data class AddInventoryRequest(
    val name: String,
    val vaccineType: String,
    val vaccineDate: String,
    val bovineId: Int,
    val image: File
) {
    companion object {
        fun fromInventory(inventory: Inventory): AddInventoryRequest {
            val file = (inventory.image as? InventoryImage.FromFile)?.file
                ?: throw IllegalArgumentException("Inventory must have a local image file to upload")

            val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatedDate = LocalDate.parse(inventory.vaccineDate,dateFormatter)

            return AddInventoryRequest(
                inventory.name,
                inventory.vaccineType,
                formatedDate.toString(),
                inventory.bovineId,
                file
            )

        }
    }
}