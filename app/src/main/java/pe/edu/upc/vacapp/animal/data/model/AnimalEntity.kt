package pe.edu.upc.vacapp.animal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.animal.domain.model.Animal
import pe.edu.upc.vacapp.animal.domain.model.AnimalImage

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
    val synced: Boolean = false
) {
    companion object {
        fun fromAnimal(animal: Animal): AnimalEntity {
            return AnimalEntity(
                id = animal.id,
                name = animal.name,
                gender = if (animal.isMale) "male" else "female",
                birthDate = animal.birthDate,
                breed = animal.breed,
                location = animal.location,
                stableId = animal.barnId,
                imagePath = animal.image.toString(),
                age = animal.age,
                synced = false
            )
        }
    }

    fun toAnimal(): Animal {
        return Animal(
            id = this.id,
            name = this.name,
            breed = this.breed,
            age = this.age,
            birthDate = this.birthDate,
            barnId = this.stableId,
            location = this.location,
            image = AnimalImage.FromUrl(this.imagePath),
            isMale = this.gender == "male"
        )
    }
}
