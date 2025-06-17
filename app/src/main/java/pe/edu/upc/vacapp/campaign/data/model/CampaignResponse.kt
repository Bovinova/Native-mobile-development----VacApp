package pe.edu.upc.vacapp.campaign.data.model

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
        return Campaign(
            name = name,
            description = description,
            startdate = startDate,
            enddate = endDate,
        )
    }


}
