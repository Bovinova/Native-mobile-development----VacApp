package pe.edu.upc.vacapp.iam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
data class TokenEntity(
    @PrimaryKey
    val id: Int = 1,
    val token: String,
)
