package pe.edu.upc.vacapp.animal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.animal.domain.model.Animal

@Entity
data class AnimalEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val gender: String,
    val birthDate: String,
    val breed: String,
    val location: String,
    val stableId: Int,
    val imagePath: String,
    val age: Int,
    val userId: Int,
    val synced: Boolean = false
) {
    companion object {
        fun fromAnimal(animal: Animal, userId: Int, animalId: Int): AnimalEntity {
            return AnimalEntity(
                id = animalId,
                name = animal.name,
                gender = if (animal.isMale) "male" else "female",
                birthDate = animal.birthDate,
                breed = animal.breed,
                location = animal.location,
                stableId = animal.barnId,
                imagePath = animal.image,
                age = animal.age,
                userId = userId,
                synced = false
            )
        }
    }

    fun toAnimal(): Animal {
        return Animal(
            id = id,
            name = name,
            breed = breed,
            age = age,
            birthDate = birthDate,
            barnId = this.stableId,
            location = location,
            isMale = this.gender == "male",
            image = imagePath
        )
    }

}
