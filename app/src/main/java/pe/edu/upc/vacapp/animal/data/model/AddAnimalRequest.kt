package pe.edu.upc.vacapp.animal.data.model

import pe.edu.upc.vacapp.animal.domain.model.Animal
import java.io.File

data class AddAnimalRequest(
    val name: String,
    val gender: String,
    val birthDate: String,
    val breed: String,
    val location: String,
    val stableId: Int,
    val image: File
) {
    companion object {
        fun fromAnimal(animal: Animal): AddAnimalRequest {
            val file = File(animal.image)

            return AddAnimalRequest(
                animal.name,
                if (animal.isMale) "male" else "female",
                animal.birthDate,
                animal.breed,
                animal.location,
                animal.barnId,
                file
            )
        }
    }
}