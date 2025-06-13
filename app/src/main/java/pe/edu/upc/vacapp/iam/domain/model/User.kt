package pe.edu.upc.vacapp.iam.domain.model

data class User(
    val username: String = "",
    val password: String = "",
    val email: String = "",
)