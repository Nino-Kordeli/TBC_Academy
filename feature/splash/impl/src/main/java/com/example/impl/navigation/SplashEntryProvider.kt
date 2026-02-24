package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.SplashNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screen.SplashScreen

fun EntryProviderScope<NavKey>.splashEntry(navigator: Navigator) {
    entry<SplashNavKey> {
        SplashScreen {
            navigator.navigateAndClearStack(it)
        }
    }
}