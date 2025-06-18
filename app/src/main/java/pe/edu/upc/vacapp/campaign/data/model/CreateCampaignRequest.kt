package pe.edu.upc.vacapp.campaign.data.model

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import pe.edu.upc.vacapp.campaign.domain.model.Campaign


data class CreateCampaignRequest(
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val status: String,
    val goals: Any,
    val channels: Any,
    val stableId: Int,
) {
    companion object {
        fun fromCampaign(c: Campaign): CreateCampaignRequest {
            val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val startDateFormated = LocalDate.parse(c.startdate, dateFormatter)
            val endDateFormated = LocalDate.parse(c.enddate, dateFormatter)

            return CreateCampaignRequest(
                name = c.name,
                description = c.description,
                startDate = startDateFormated.toString(),
                endDate = endDateFormated.toString(),
                status = "",
                goals = emptyList<Any>(),
                channels = emptyList<Any>(),
                stableId = 1
            )

        }
    }
}
