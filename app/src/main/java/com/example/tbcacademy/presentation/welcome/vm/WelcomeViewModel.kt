package com.example.myapplication.presentation.welcome.vm

import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.myapplication.presentation.welcome.contract.WelcomeEvent
import com.example.myapplication.presentation.welcome.contract.WelcomeSideEffect
import com.example.myapplication.presentation.welcome.contract.WelcomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : BaseViewModel
<WelcomeUiState, WelcomeEvent, WelcomeSideEffect>(WelcomeUiState()) {

    override fun onEvent(event: WelcomeEvent) {
        super.onEvent(event)

        when (event) {
            WelcomeEvent.LoginClicked -> {
                emitSideEffect(WelcomeSideEffect.NavigateToLogin)
            }

            WelcomeEvent.RegisterClicked -> {
                emitSideEffect(WelcomeSideEffect.NavigateToRegister)
            }
        }
    }
}