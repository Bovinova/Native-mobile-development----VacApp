package pe.edu.upc.vacapp.animal.domain.model

data class Animal(
    val id: Int = 0,
    val name: String = "",
    val breed: String = "",
    val weight: Double = 0.0,
    val age: Int = 0,
    val birthDate: String = "",
    val barnId: Int = 0,
    val location: String = "",
    val image: AnimalImage? = null,
    val isMale: Boolean = true
)