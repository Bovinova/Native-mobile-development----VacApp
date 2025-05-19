package pe.edu.upc.vacapp.data.model

import com.google.gson.annotations.SerializedName
import pe.edu.upc.vacapp.domain.model.Bovine

data class BovineResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("birthDate") val birthDate: String?,
    @SerializedName("breed") val breed: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("bovineImg") val bovineImg: String?,
    @SerializedName("stableId") val stableId: Int?
)

fun BovineResponse.toBovine(): Bovine {
    return Bovine(
        id = id ?: 0,
        name = name ?: "",
        gender = gender ?: "",
        birthDate = birthDate ?: "",
        breed = breed ?: "",
        location = location ?: "",
        bovineImg = bovineImg ?: "",
        stableId = stableId ?: 0,

    )
}