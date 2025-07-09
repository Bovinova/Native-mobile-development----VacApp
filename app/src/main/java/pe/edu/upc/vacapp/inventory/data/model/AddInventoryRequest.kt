package pe.edu.upc.vacapp.inventory.data.model

import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import java.io.File

data class AddInventoryRequest(
    val name: String,
    val vaccineType: String,
    val vaccineDate: String,
    val bovineId: Int,
    val image: File
) {
    companion object {
        fun fromInventory(inventory: Inventory): AddInventoryRequest {
            val file = File(inventory.image)

            return AddInventoryRequest(
                inventory.name,
                inventory.vaccineType,
                inventory.vaccineDate,
                inventory.bovineId,
                file
            )

        }
    }
}