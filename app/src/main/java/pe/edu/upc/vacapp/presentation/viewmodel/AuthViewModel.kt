package pe.edu.upc.vacapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.data.repository.AuthRepository


class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _user = MutableStateFlow<String?>(null)
    val user: StateFlow<String?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error



    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _user.value = response.token
            } catch (e: Exception) {
                _error.value = when (e) {
                    is java.net.UnknownHostException -> "No hay conexión a Internet. Por favor, verifica tu conexión."
                    is java.net.SocketTimeoutException -> "La conexión ha expirado. Intenta nuevamente en unos momentos."
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            500 -> "Estamos experimentando algunos problemas. Por favor, inténtalo más tarde. ¡Gracias por tu paciencia!"
                            else -> "Error del servidor: ${e.code()} - ${e.message()}"
                        }
                    }
                    else -> "Error al iniciar sesión: ${e.localizedMessage ?: "Ocurrió un problema inesperado."}"
                }
            }
        }
    }


    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(username, password, email)
                _user.value = response.token
            } catch (e: Exception) {
                _error.value = when (e) {
                    is java.net.UnknownHostException -> "No hay conexión a Internet. Por favor, verifica tu conexión."
                    is java.net.SocketTimeoutException -> "La conexión ha expirado. Intenta nuevamente en unos momentos."
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            400 -> "Datos inválidos. Revisa que el correo electrónico no esté registrado y que la contraseña cumpla con los requisitos."
                            409 -> "El usuario o correo electrónico ya están registrados."
                            500 -> "Estamos teniendo dificultades técnicas. Intenta registrarte más tarde. ¡Gracias por tu comprensión!"
                            else -> "Error del servidor: ${e.code()} - ${e.message()}"
                        }
                    }
                    else -> "Error al registrarse: ${e.localizedMessage ?: "Ocurrió un problema inesperado."}"
                }
            }
        }
    }

    // En AuthViewModel.kt
    fun logout() {
        _user.value = null
        _error.value = null
        // Si guardas el token en preferencias, bórralo aquí también
    }

}

