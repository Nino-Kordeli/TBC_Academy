package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.AuthenticationNavKey
import com.example.api.QuizNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.login.screen.LoginScreen
import com.example.impl.screens.register.screen.RegisterScreen
import com.example.impl.screens.welcome.screen.WelcomeScreen

fun EntryProviderScope<NavKey>.welcomeEntry(navigator: Navigator) {
    entry<AuthenticationNavKey.WelcomeNavKey> {
        WelcomeScreen(
            onLoginClick = {
                navigator.navigate(AuthenticationNavKey.LoginNavKey)
            },
            onRegisterClick = {
                navigator.navigate(AuthenticationNavKey.RegisterNavKey)
            }
        )
    }
}

fun EntryProviderScope<NavKey>.loginEntry(navigator: Navigator) {
    entry<AuthenticationNavKey.LoginNavKey> {
        LoginScreen(
            onNavigateHome = {},
            onNavigateToRegister = {},
            onShowError = {}
        )
    }
}

fun EntryProviderScope<NavKey>.registerEntry(navigator: Navigator) {
    entry<AuthenticationNavKey.RegisterNavKey> {
        RegisterScreen(
            onNavigateQuiz = {
                 navigator.navigate(QuizNavKey.QuizKey)
            },
            onNavigateToLogin = {},
            onShowError = {}
        )
    }
}
