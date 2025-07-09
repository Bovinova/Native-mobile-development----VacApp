package pe.edu.upc.vacapp.animal.data.model

import com.google.gson.annotations.SerializedName
import pe.edu.upc.vacapp.animal.domain.model.Animal

data class AnimalJson(
    val id: Int,
    val name: String,
    val breed: String,
    val age: Int,
    val birthDate: String,
    @SerializedName("stableId")
    val barnId: Int,
    val location: String,
    @SerializedName("imagePath")
    val image: String,
    val gender: String
) {
    fun toAnimal(): Animal {
        return Animal(
            id = id,
            name = name,
            breed = breed,
            age = age,
            birthDate = birthDate,
            barnId = barnId,
            location = location,
            image = image,
            isMale = gender.lowercase() == "male"
        )
    }
}
