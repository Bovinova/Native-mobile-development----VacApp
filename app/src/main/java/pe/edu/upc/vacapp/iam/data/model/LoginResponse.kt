package pe.edu.upc.vacapp.iam.data.model

data class LoginResponse(
    val token: String
){
    fun toTokenEntity(): TokenEntity {
        return TokenEntity(token = token)
    }
}
