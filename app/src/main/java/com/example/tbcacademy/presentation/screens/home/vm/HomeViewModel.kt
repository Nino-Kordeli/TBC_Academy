package com.example.tbcacademy.presentation.screens.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<com.example.tbcacademy.domain.model.User>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getUsersUseCase().collect { result ->
                when (result) {
                    is com.example.tbcacademy.domain.model.Result.Success -> {
                        _uiState.value = HomeUiState.Success(result.data)
                    }
                    is com.example.tbcacademy.domain.model.Result.Error -> {
                        _uiState.value = HomeUiState.Error(result.exception.message ?: "Failed")
                    }
                    else -> {}
                }
            }
        }
    }
}