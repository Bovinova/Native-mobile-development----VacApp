package pe.edu.upc.vacapp.iam.data.model

import pe.edu.upc.vacapp.iam.domain.model.User

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
) {
    companion object {
        fun fromUser(user: User): RegisterRequest {
            return RegisterRequest(
                username = user.username,
                password = user.password,
                email = user.email
            )
        }
    }
}
