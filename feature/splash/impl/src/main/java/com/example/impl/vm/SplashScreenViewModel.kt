package com.example.impl.vm

import androidx.lifecycle.viewModelScope
import com.example.api.AuthenticationNavKey
import com.example.api.DashboardNavKey
import com.example.domain.usecase.auth.IsLoggedInUseCase
import com.example.impl.contract.SplashScreenEvent
import com.example.impl.contract.SplashScreenSideEffect
import com.example.impl.contract.SplashScreenState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel<SplashScreenState, SplashScreenEvent, SplashScreenSideEffect>(
    SplashScreenState()
) {

    init {
        waitForAnimation()
        handleNavigation()
    }

    private fun waitForAnimation() {
        viewModelScope.launch {
            delay(2500)
            handleNavigation()
        }
    }

    private fun handleNavigation() {
        val isLoggedIn = isLoggedInUseCase.invoke()

        val navKey = if (isLoggedIn) {
            DashboardNavKey.HomeNavKey
        } else {
            AuthenticationNavKey.WelcomeNavKey
        }

        emitSideEffect(SplashScreenSideEffect.Navigate(navKey))
    }
}