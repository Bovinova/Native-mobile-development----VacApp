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
        val formatterWithMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun parseDateFlexible(date: String): LocalDateTime {
            return try {
                LocalDateTime.parse(date, formatterWithMillis)
            } catch (e: Exception) {
                LocalDateTime.parse(date, formatterWithoutMillis)
            }
        }

        val startDateTime = parseDateFlexible(startDate)
        val endDateTime = parseDateFlexible(endDate)

        return Campaign(
            name = name,
            description = description,
            startdate = startDateTime.format(outputFormatter),
            enddate = endDateTime.format(outputFormatter),
        )
    }
}
