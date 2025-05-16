package pe.edu.upc.vacapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.data.model.LoginRequest
import pe.edu.upc.vacapp.data.model.RegisterRequest
import pe.edu.upc.vacapp.data.remote.AuthApiService
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
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(username, email, password)
                _user.value = response.token
            } catch (e: Exception) {
                _error.value = "Error al registrarse: ${e.message}"
            }
        }
    }
}

