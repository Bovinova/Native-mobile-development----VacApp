package pe.edu.upc.vacapp.data.model

import com.google.gson.annotations.SerializedName
import pe.edu.upc.vacapp.domain.model.Bovine

data class BovineResponse(
    @SerializedName("Name") val name: String?,
    @SerializedName("Gender") val gender: String?,
    @SerializedName("BirthDate") val birthDate: String?,
    @SerializedName("Breed") val breed: String?,
    @SerializedName("Location") val location: String?,
    @SerializedName("StableId") val stableId: Int?,
    @SerializedName("fileData") val fileData: String?
)

fun BovineResponse.toBovine(): Bovine {
    return Bovine(
        name = name ?: "",
        gender = gender ?: "",
        breed = breed ?: "",
        stableId = stableId ?: 0
    )
}
