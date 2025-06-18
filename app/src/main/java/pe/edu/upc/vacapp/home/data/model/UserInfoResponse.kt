package pe.edu.upc.vacapp.home.data.model

import pe.edu.upc.vacapp.home.domain.model.UserInfo

data class UserInfoResponse(
    val name: String,
    val totalAnimals: Int,
    val totalCampaigns: Int,
    val totalStables: Int
) {
    fun toUserInfo(): UserInfo {
        return UserInfo(
            name = name,
            totalAnimals = totalAnimals,
            totalCampaigns = totalCampaigns,
            totalBarns = totalStables
        )
    }
}
