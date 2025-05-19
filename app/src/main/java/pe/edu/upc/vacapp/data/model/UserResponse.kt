package pe.edu.upc.vacapp.data.model


data class UserResponse(
    val username: String,
    val email: String,
    val password: String,
    val token: String
)