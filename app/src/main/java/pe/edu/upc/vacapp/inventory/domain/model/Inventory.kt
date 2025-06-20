package pe.edu.upc.vacapp.inventory.domain.model

import java.util.Date

data class Inventory(
    val id: Int? = null,
    val name: String = "",
    val vaccineType: String = "",
    val vaccineDate: String = "",
    val bovineId: Int = 0,
    val image: InventoryImage? = null
)