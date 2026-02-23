package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.DashboardNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.dashboard.screen.DashboardScreen

fun EntryProviderScope<NavKey>.homeEntry(
    navigator: Navigator
) {
    entry<DashboardNavKey.HomeNavKey> {
        DashboardScreen(
            onSearchClick = {
                navigator.navigate(DashboardNavKey.SearchNavKey)
            },
            navigator = navigator
        )
    }
}

fun EntryProviderScope<NavKey>.searchNavEntry(
    navigator: Navigator
) {
    entry<DashboardNavKey.SearchNavKey> {
//        DashboardScreen(
//            onSearchClick = {},
//            navigator = navigator
//        )
    }
}