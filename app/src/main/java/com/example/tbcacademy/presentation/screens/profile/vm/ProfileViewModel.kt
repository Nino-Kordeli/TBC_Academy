package com.example.tbcacademy.presentation.screens.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.repository.TokenRepository
import com.example.tbcacademy.presentation.screens.profile.model.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val token = tokenRepository.readToken()
            val username = tokenRepository.readUsername()
            val email = tokenRepository.readEmail()
            _uiState.value = ProfileUiState(token, username, email)
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenRepository.clearAll()
            _uiState.value = ProfileUiState()
        }
    }
}
