package pe.edu.upc.vacapp.campaign.data.model

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
    fun toCampaignEntity(userId: Int): CampaignEntity {
        return CampaignEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            startDate = this.startDate,
            endDate = this.endDate,
            barnId = this.stableId,
            userId = userId,
            synced = true
        )
    }
}
