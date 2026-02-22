package com.example.impl.vm

import androidx.lifecycle.viewModelScope
import com.example.api.AuthenticationNavKey
import com.example.api.DashboardNavKey
import com.example.common.resource.Resource
import com.example.domain.repository.UserSessionRepository
import com.example.domain.usecase.auth.LoginUseCase
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
    private val loginUseCase: LoginUseCase,
    private val userSessionRepository: UserSessionRepository
) : BaseViewModel<SplashScreenState, SplashScreenEvent, SplashScreenSideEffect>(
    SplashScreenState()
) {

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            waitForAnimation()

            val rememberMe = userSessionRepository.getRememberMe()
            val savedEmail = userSessionRepository.getSavedEmail()
            val savedPassword = userSessionRepository.getSavedPassword()

            if (rememberMe) {
                login(savedEmail ?: "", savedPassword ?: "")
            } else {
                handleNavigation(false)
            }
        }
    }

    private suspend fun login(email: String, password: String) {
        loginUseCase.invoke(email, password).collect { result ->
            when (result) {
                is Resource.Success<String> -> {
                    handleNavigation(true)
                }

                else -> {
                    handleNavigation(isLoggedIn = false)
                }
            }
        }
    }

    private fun handleNavigation(isLoggedIn: Boolean) {
        val navKey = if (isLoggedIn) {
            DashboardNavKey.HomeNavKey
        } else {
            AuthenticationNavKey.WelcomeNavKey
        }

        emitSideEffect(SplashScreenSideEffect.Navigate(navKey))
    }

    private suspend fun waitForAnimation() {
        delay(2500)
    }
}