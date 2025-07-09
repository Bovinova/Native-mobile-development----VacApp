package pe.edu.upc.vacapp.inventory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.inventory.domain.model.Inventory

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
        fun fromInventory(inventory: Inventory, userId: Int, inventoryId: Int): InventoryEntity {
            return InventoryEntity(
                id = inventoryId,
                name = inventory.name,
                vaccineType = inventory.vaccineType,
                vaccineDate = inventory.vaccineDate,
                bovineId = inventory.bovineId,
                imagePath = inventory.image,
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
            image = imagePath
        )
    }
}
