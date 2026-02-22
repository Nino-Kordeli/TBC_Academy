package com.example.impl.contract

import androidx.navigation3.runtime.NavKey

data class SplashScreenState(
    val isLoading: Boolean = false
)

sealed interface SplashScreenEvent {

}

sealed class SplashScreenSideEffect {
    data class Navigate(val navKey: NavKey) : SplashScreenSideEffect()
}
