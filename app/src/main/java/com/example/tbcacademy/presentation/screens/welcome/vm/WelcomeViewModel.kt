package com.example.tbcacademy.presentation.screens.welcome.vm

import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.navigation.Routes
import com.example.tbcacademy.presentation.screens.welcome.contract.WelcomeEvent
import com.example.tbcacademy.presentation.screens.welcome.contract.WelcomeSideEffect
import com.example.tbcacademy.presentation.screens.welcome.contract.WelcomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() :
    BaseViewModel<WelcomeState, WelcomeEvent, WelcomeSideEffect>(WelcomeState()) {

    override fun onEvent(event: WelcomeEvent) {
        when (event) {
            WelcomeEvent.LoginClicked -> emitSideEffect(
                WelcomeSideEffect.NavigateToLogin(Routes.LOGIN)
            )
            WelcomeEvent.RegisterClicked -> emitSideEffect(
                WelcomeSideEffect.NavigateToRegister(Routes.REGISTER)
            )
        }
    }
}