package com.example.challenge.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent: SharedFlow<SplashUiEvent> get() = _uiEvent

    sealed interface SplashUiEvent {
        data object NavigateToConnections : SplashUiEvent
        data object NavigateToLogIn : SplashUiEvent
    }

    init {
        viewModelScope.launch {
            val isLoggedIn = checkIfUserIsLoggedIn()
            if (isLoggedIn) {
                _uiEvent.emit(SplashUiEvent.NavigateToConnections)
            } else {
                _uiEvent.emit(SplashUiEvent.NavigateToLogIn)
            }
        }
    }

    private suspend fun checkIfUserIsLoggedIn(): Boolean {
        return false
    }
}