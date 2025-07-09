package pe.edu.upc.vacapp.animal.data.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

data class AnimalResponse(
    val id: Int,
    val name: String,
    val gender: String,
    val birthDate: String,
    val breed: String,
    val location: String,
    val bovineImg: String,
    val stableId: Int
) {
    fun toAnimalEntity(userId: Int): AnimalEntity {
        val localDateTime = try {
            LocalDateTime.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
        } catch (e: Exception) {
            LocalDateTime.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        }

        val birthDateOnly = localDateTime.toLocalDate()
        val today = LocalDate.now()

        val age = if ((today.monthValue > birthDateOnly.monthValue) ||
            (today.monthValue == birthDateOnly.monthValue && today.dayOfMonth >= birthDateOnly.dayOfMonth)
        ) {
            today.year - birthDateOnly.year
        } else {
            today.year - birthDateOnly.year - 1
        }

        return AnimalEntity(
            id = this.id,
            name = this.name,
            gender = this.gender,
            birthDate = this.birthDate,
            age = age,
            breed = this.breed,
            location = this.location,
            stableId = this.stableId,
            imagePath = this.bovineImg,
            userId = userId,
            synced = true
        )
    }
}
