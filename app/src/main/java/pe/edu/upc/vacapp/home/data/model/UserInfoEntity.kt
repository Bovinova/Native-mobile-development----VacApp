package pe.edu.upc.vacapp.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.home.domain.model.NextCampaign
import pe.edu.upc.vacapp.home.domain.model.UserInfo

@Entity
data class UserInfoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val totalAnimals: Int,
    val totalCampaigns: Int,
    val totalBarns: Int,
    val totalVaccinations: Int,
    val userId: Int // Este es el mismo que JwtStorage.getUserId()
) {
    companion object {
        fun fromUserInfo(userInfo: UserInfo, userId: Int): UserInfoEntity {
            return UserInfoEntity(
                id = userInfo.id,
                name = userInfo.name,
                totalAnimals = userInfo.totalAnimals,
                totalCampaigns = userInfo.totalCampaigns,
                totalBarns = userInfo.totalBarns,
                totalVaccinations=userInfo.totalVaccinations,
                userId = userId
            )
        }
    }

    fun toUserInfo(nextCampaigns: List<NextCampaign>): UserInfo {
        return UserInfo(
            id = this.id,
            name = this.name,
            totalAnimals = this.totalAnimals,
            totalCampaigns = this.totalCampaigns,
            totalBarns = this.totalBarns,
            totalVaccinations = this.totalVaccinations,
            nextCampaigns = nextCampaigns
        )
    }
}
