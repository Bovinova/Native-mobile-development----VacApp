package pe.edu.upc.vacapp.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import pe.edu.upc.vacapp.home.domain.model.NextCampaign

@Entity
data class NextCampaignEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val startDate: String,
    val userInfoId: Int
) {
    companion object {
        fun fromNextCampaign(campaign: NextCampaign, userInfoId: Int): NextCampaignEntity {
            return NextCampaignEntity(
                id = campaign.id,
                name = campaign.name,
                startDate = campaign.startDate.toString(),
                userInfoId = userInfoId
            )
        }
    }

    fun toNextCampaign(): NextCampaign {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return NextCampaign(
            id = this.id,
            name = this.name,
            startDate = LocalDateTime.parse(this.startDate, formatter)
        )
    }
}
