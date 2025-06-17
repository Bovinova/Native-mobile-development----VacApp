package pe.edu.upc.vacapp.campaign.data.model

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

            return CreateCampaignRequest(
                name = c.name,
                description = c.description,
                startDate = c.startdate,
                endDate = c.enddate,
                status = "",
                goals = emptyList<Any>(),
                channels = emptyList<Any>(),
                stableId = 1
            )

        }
    }
}
