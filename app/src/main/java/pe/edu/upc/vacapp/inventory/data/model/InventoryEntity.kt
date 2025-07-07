package pe.edu.upc.vacapp.inventory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.inventory.domain.model.InventoryImage
import java.io.File

@Entity
data class InventoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val vaccineType: String,
    val vaccineDate: String,
    val bovineId: Int,
    val imagePath: String,
    val userId: Int,
    val synced: Boolean = false
) {
    companion object {
        fun fromInventory(inventory: Inventory, userId: Int): InventoryEntity {
            return InventoryEntity(
                id = inventory.id ?: 0,
                name = inventory.name,
                vaccineType = inventory.vaccineType,
                vaccineDate = inventory.vaccineDate,
                bovineId = inventory.bovineId,
                imagePath = inventory.image.toString(),
                userId = userId,
                synced = false
            )
        }
    }

    fun toInventory(): Inventory {
        return Inventory(
            id = this.id,
            name = this.name,
            vaccineType = this.vaccineType,
            vaccineDate = this.vaccineDate,
            bovineId = this.bovineId,
            image = if (imagePath.startsWith("/")) {
                InventoryImage.FromFile(File(imagePath))
            } else {
                InventoryImage.FromUrl(imagePath)
            }
        )
    }
}
