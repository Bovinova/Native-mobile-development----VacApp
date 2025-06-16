package pe.edu.upc.vacapp.iam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.iam.data.repository.AuthRepository
import pe.edu.upc.vacapp.iam.domain.model.User

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    fun updateEmail(email: String) {
        _user.value = _user.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _user.value = _user.value.copy(password = password)
    }

    fun updateUsername(username: String) {
        _user.value = _user.value.copy(username = username)
    }

    fun clearUser() {
        _user.value = User()
    }

    fun login() {
        viewModelScope.launch {
            _loginSuccess.value = authRepository.login(_user.value)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            clearUser()
        }
    }

    fun register() {
        viewModelScope.launch {
            _loginSuccess.value = authRepository.register(_user.value)
        }
    }

    fun resetLoginSuccess() {
        _loginSuccess.value = null
    }
}