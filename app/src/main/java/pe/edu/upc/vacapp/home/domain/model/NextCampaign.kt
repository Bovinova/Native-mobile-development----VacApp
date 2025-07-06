package pe.edu.upc.vacapp.home.domain.model

import org.threeten.bp.LocalDateTime

data class NextCampaign(
    val id: Int,
    val name: String,
    val startDate: LocalDateTime
)
