package pe.edu.upc.vacapp.home.data.model

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import pe.edu.upc.vacapp.home.domain.model.NextCampaign

data class NextCampaignResponse(
    val id: Int,
    val name: String,
    val startDate: String
){
    fun toNextCampaign(): NextCampaign {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]")

        return NextCampaign(
            id = id,
            name = name,
            startDate = LocalDateTime.parse(startDate, formatter)
        )
    }
}
