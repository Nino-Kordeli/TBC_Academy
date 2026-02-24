package com.example.impl.screens.welcome.vm

import com.example.impl.screens.welcome.contract.WelcomeEvent
import com.example.impl.screens.welcome.contract.WelcomeSideEffect
import com.example.ui.base.BaseViewModel
import com.example.ui.base.empty_case.NoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() :
    BaseViewModel<NoState, WelcomeEvent, WelcomeSideEffect>(NoState) {

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