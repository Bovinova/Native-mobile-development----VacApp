package pe.edu.upc.vacapp.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.vacapp.home.data.repository.UserInfoRepository
import pe.edu.upc.vacapp.home.domain.model.UserInfo

class HomeViewModel(
    private val getUserInfoRepository: UserInfoRepository
) : ViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo())
    val userInfo: StateFlow<UserInfo> = _userInfo

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = getUserInfoRepository.getUserInfo()
        }
    }
}