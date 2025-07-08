package pe.edu.upc.vacapp.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PendingOperationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entity: String,
    val type: String,
    val dataJson: String,
    val localId: Int
)
