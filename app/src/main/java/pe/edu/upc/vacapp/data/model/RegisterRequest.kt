package pe.edu.upc.vacapp.data.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
)