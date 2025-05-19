package pe.edu.upc.vacapp.domain.model

data class User(
    var token: String,
    val username: String,
    val email: String,
    val password: String,
)
