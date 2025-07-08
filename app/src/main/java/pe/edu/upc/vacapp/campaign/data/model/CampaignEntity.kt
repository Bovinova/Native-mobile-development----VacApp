package pe.edu.upc.vacapp.campaign.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.campaign.domain.model.Campaign

@Entity
data class CampaignEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val barnId: Int,
    val userId: Int,
    val synced: Boolean = false
){
    companion object {
        fun fromCampaign(c: Campaign, userId: Int, generatedId: Int): CampaignEntity {
            return CampaignEntity(
                id = generatedId,
                name = c.name,
                description = c.description,
                startDate = c.startDate,
                endDate = c.endDate,
                barnId = c.barnId,
                userId = userId,
                synced = false
            )
        }
    }

    fun toCampaign(): Campaign {
        return Campaign(
            id = this.id,
            name = this.name,
            description = this.description,
            startDate = this.startDate,
            endDate = this.endDate,
            barnId = this.barnId
        )
    }

}