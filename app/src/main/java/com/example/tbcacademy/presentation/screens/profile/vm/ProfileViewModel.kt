package com.example.tbcacademy.presentation.screens.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.GetUserEmailUseCase
import com.example.tbcacademy.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileState(val email: String = "")
object ProfileEvent

class ProfileViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events = _events

    init {
        viewModelScope.launch {
            val email = getUserEmailUseCase() ?: ""
            _state.value = ProfileState(email)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _events.emit(ProfileEvent)
        }
    }
}