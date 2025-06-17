package pe.edu.upc.vacapp.campaign.data.model

import org.threeten.bp.LocalDate
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
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val startDateTime = LocalDateTime.parse(startDate, dateFormatter)
        val endDateTime = LocalDateTime.parse(endDate, dateFormatter)
        val formattedStartDateTime = startDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val formattedEndDateTime = endDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        return Campaign(
            name = name,
            description = description,
            startdate = formattedStartDateTime,
            enddate = formattedEndDateTime,
        )
    }


}
