package pe.edu.upc.vacapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.data.model.UserResponse
import pe.edu.upc.vacapp.data.repository.AuthRepository
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn



    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _user.value = response
                _error.value = null
                _isLoggedIn.value = true
                onSuccess()
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                _error.value = message
                _isLoggedIn.value = false
                onFailure(message)
            }
        }
    }



    fun register(username: String, password: String, email: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(username, password, email)
                _user.value = response
                _isLoggedIn.value = true
            } catch (e: Exception) {
                _error.value = getErrorMessage(e)
                _isLoggedIn.value = false
            }
        }
    }

    fun logout() {
        Log.d("AuthViewModel", "Cerrando sesión")
        _user.value = null
        _error.value = null
        _isLoggedIn.value = false
    }

    private fun getErrorMessage(e: Exception): String {
        return when (e) {
            is UnknownHostException -> "No hay conexión a Internet. Por favor, verifica tu conexión."
            is SocketTimeoutException -> "La conexión ha expirado. Intenta nuevamente en unos momentos."
            is HttpException -> when (e.code()) {
                400 -> "Datos inválidos. Revisa que el correo electrónico no esté registrado y que la contraseña cumpla con los requisitos."
                409 -> "El usuario o correo electrónico ya están registrados."
                500 -> "Estamos experimentando algunos problemas. Por favor, inténtalo más tarde. ¡Gracias por tu paciencia!"
                else -> "Error del servidor: ${e.code()} - ${e.message()}"
            }
            else -> "Error inesperado: ${e.localizedMessage ?: "Ocurrió un problema desconocido."}"
        }
    }
}
