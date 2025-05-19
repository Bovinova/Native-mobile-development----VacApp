package pe.edu.upc.vacapp.domain.model

data class Bovine(
    val id: Int,
    val name: String,
    val gender: String,
    val birthDate: String,
    val breed: String,
    val location: String,
    val bovineImg: String,
    val stableId: Int,
)
