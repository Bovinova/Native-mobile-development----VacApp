package pe.edu.upc.vacapp.inventory.data.model

import android.annotation.SuppressLint
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.temporal.ChronoUnit
import pe.edu.upc.vacapp.inventory.domain.model.Inventory
import pe.edu.upc.vacapp.inventory.domain.model.InventoryImage

data class InventoryResponse(
    val id: Int,
    val name: String,
    val vaccineType: String,
    val vaccineDate: String,
    val bovineId: Int,
    val vaccineImg: String
) {
    @SuppressLint("DefaultLocale")
    fun toInventory(): Inventory {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
        val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val today = LocalDate.now()

        val parsedDate = try {
            val dateTime = LocalDateTime.parse(vaccineDate, inputFormat)
            dateTime.toLocalDate()
        } catch (e: DateTimeParseException) {
            try {
                LocalDate.parse(vaccineDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            } catch (e2: Exception) {
                today
            }
        }

        val formattedDate = parsedDate.format(outputFormat)

        return Inventory(
            id = id,
            name = name,
            vaccineType = vaccineType,
            vaccineDate = formattedDate,
            bovineId = bovineId,
            image = InventoryImage.FromUrl(vaccineImg)
        )
    }

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
