package pe.edu.upc.vacapp.inventory.data.model

data class InventoryResponse(
    val id: Int,
    val name: String,
    val vaccineType: String,
    val vaccineDate: String,
    val bovineId: Int,
    val vaccineImg: String
) {
    fun toInventoryEntity(userId: Int): InventoryEntity {
        return InventoryEntity(
            id = this.id,
            name = this.name,
            vaccineType = this.vaccineType,
            vaccineDate = this.vaccineDate,
            bovineId = this.bovineId,
            imagePath = this.vaccineImg,
            userId = userId,
            synced = true
        )
    }
}
