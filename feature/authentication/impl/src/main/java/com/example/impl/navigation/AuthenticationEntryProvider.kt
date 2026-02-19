package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.AuthenticationNavKey
import com.example.api.DashboardNavKey
import com.example.api.QuizNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.login.screen.LoginScreen
import com.example.impl.screens.register.screen.RegisterScreen
import com.example.impl.screens.welcome.screen.WelcomeScreen
import com.example.ui.snackbar.SnackbarController

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

fun EntryProviderScope<NavKey>.loginEntry(
    navigator: Navigator,
    snackbarController: SnackbarController
) {
    entry<AuthenticationNavKey.LoginNavKey> {
        LoginScreen(
            onNavigateHome = {
                navigator.navigateAndClearStack(DashboardNavKey.HomeNavKey())
            },
            onNavigateToRegister = {
                navigator.navigate(AuthenticationNavKey.RegisterNavKey)
            },
            onShowError = { message ->
                snackbarController.showError(message)
            }
        )
    }
}

fun EntryProviderScope<NavKey>.registerEntry(
    navigator: Navigator,
    snackbarController: SnackbarController
) {
    entry<AuthenticationNavKey.RegisterNavKey> {
        RegisterScreen(
            onNavigateQuiz = {
                navigator.navigateAndClearStack(QuizNavKey.QuizKey)
            },
            onNavigateToLogin = {
                navigator.replace(AuthenticationNavKey.LoginNavKey)
            },
            onShowError = { message ->
                snackbarController.showError(message)
            }
        )
    }
}