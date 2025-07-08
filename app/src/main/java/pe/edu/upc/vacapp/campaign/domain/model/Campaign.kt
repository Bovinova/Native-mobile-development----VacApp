package pe.edu.upc.vacapp.campaign.domain.model

data class Campaign(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val barnId: Int = 0,
)
