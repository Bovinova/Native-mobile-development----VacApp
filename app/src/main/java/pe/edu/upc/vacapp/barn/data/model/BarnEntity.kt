package pe.edu.upc.vacapp.barn.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.edu.upc.vacapp.barn.domain.model.Barn

@Entity
data class BarnEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val limit: String,
    val userId: Int,
    val synced: Boolean = false
) {
    companion object {
        fun fromBarn(barn: Barn, userId: Int): BarnEntity {
            return BarnEntity(
                id = barn.id,
                name = barn.name,
                limit = barn.limit,
                userId = userId,
                synced = false
            )
        }
    }

    fun toBarn(): Barn {
        return Barn(
            id = this.id,
            name = this.name,
            limit = this.limit
        )
    }
}