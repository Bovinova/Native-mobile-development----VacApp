package pe.edu.upc.vacapp.campaign.data.model

data class CreateGoalsRequest(
    val description: String,
    val metric: String,
    val targetValue: String,
    val currentValue: String
)



