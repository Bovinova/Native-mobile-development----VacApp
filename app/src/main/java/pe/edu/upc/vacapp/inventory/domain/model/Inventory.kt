package pe.edu.upc.vacapp.inventory.domain.model

import com.google.gson.annotations.SerializedName

data class Inventory(
    val id: Int = 0,
    val name: String = "",
    val vaccineType: String = "",
    val vaccineDate: String = "",
    val bovineId: Int = 0,
    @SerializedName("imagePath")
    val image: String = ""
)