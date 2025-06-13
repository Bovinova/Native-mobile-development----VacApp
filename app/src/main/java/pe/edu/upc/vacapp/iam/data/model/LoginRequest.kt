package pe.edu.upc.vacapp.iam.data.model

import pe.edu.upc.vacapp.iam.domain.model.User

data class LoginRequest(
    val email: String,
    val password: String
) {
    companion object {
        fun fromUser(user: User): LoginRequest {
            return LoginRequest(
                email = user.email,
                password = user.password
            )
        }
    }
}
