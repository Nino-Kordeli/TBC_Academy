package com.example.impl.contract

import androidx.navigation3.runtime.NavKey

sealed class SplashScreenSideEffect {
    data class Navigate(val navKey: NavKey) : SplashScreenSideEffect()
}
