package com.example.impl.screens.welcome.vm

import com.example.impl.screens.welcome.contract.WelcomeEvent
import com.example.impl.screens.welcome.contract.WelcomeSideEffect
import com.example.impl.screens.welcome.contract.WelcomeState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() :
    BaseViewModel<WelcomeState, WelcomeEvent, WelcomeSideEffect>(WelcomeState()) {

    override fun onEvent(event: WelcomeEvent) {
        when (event) {
            WelcomeEvent.LoginClicked -> emitSideEffect(
                WelcomeSideEffect.NavigateToLogin
            )

            WelcomeEvent.RegisterClicked -> emitSideEffect(
                WelcomeSideEffect.NavigateToRegister
            )
        }
    }
}