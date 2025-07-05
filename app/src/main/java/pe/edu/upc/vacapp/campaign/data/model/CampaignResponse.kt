package pe.edu.upc.vacapp.campaign.data.model

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import pe.edu.upc.vacapp.campaign.domain.model.Campaign

data class CampaignResponse(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val status: String,
    val goals: Any,
    val channel: Any,
    val stableId: Int,
) {
    fun toCampaign(): Campaign {
        val fallbackFormatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        )
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun parseDateFlexible(date: String): LocalDateTime {
            return try {
                // Intenta con el ISO primero (más estándar y tolerante)
                LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (e: Exception) {
                // Si falla, intenta con los formatos definidos
                for (formatter in fallbackFormatters) {
                    try {
                        return LocalDateTime.parse(date, formatter)
                    } catch (_: Exception) {
                    }
                }
                throw IllegalArgumentException("Invalid date format: $date")
            }
        }

        val startDateTime = parseDateFlexible(startDate)
        val endDateTime = parseDateFlexible(endDate)

        return Campaign(
            barnId = stableId,
            name = name,
            description = description,
            startdate = startDateTime.format(outputFormatter),
            enddate = endDateTime.format(outputFormatter),
        )
    }
}
