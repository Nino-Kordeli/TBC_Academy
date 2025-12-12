package com.example.tbcacademy.presentation.fragment.user_details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.datastore.UserPreferences
import com.example.tbcacademy.presentation.fragment.user_details.contract.UserDetailEvent
import com.example.tbcacademy.presentation.fragment.user_details.contract.UserDetailSideEffect
import com.example.tbcacademy.presentation.fragment.user_details.contract.UserDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state

    private val _sideEffect = MutableSharedFlow<UserDetailSideEffect>()
    val sideEffect = _sideEffect

    init {
        loadUserEmail()
    }

    private fun loadUserEmail() {
        viewModelScope.launch {
            userPreferences.getEmail.collect { email ->
                _state.value = _state.value.copy(userEmail = email)
            }
        }
    }

    fun onEvent(event: UserDetailEvent) {
        when (event) { UserDetailEvent.Logout -> logout() }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            userPreferences.clearLogin()  // <-- IMPORTANT

            _state.value = _state.value.copy(loading = false)

            _sideEffect.emit(UserDetailSideEffect.NavigateToWelcome)
        }
    }
}
