package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.DashboardNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.dashboard.screen.DashboardScreen

fun EntryProviderScope<NavKey>.dashboardEntry(navigator: Navigator) {
    entry<DashboardNavKey.HomeNavKey> {
        DashboardScreen(
            goalCalories = 2000
        )
    }
}