package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.DashboardNavKey
import com.example.api.RecipesNavKey
import com.example.api.WorkoutNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.dashboard.screen.DashboardScreen

fun EntryProviderScope<NavKey>.homeEntry(
    navigator: Navigator
) {
    entry<DashboardNavKey.HomeNavKey> {
        DashboardScreen(
            onWorkoutClick = { navigator.navigate(WorkoutNavKey.WorkoutNavKey) },
            onRecipeClick = { navigator.navigate(RecipesNavKey.RecipesNavKey) }
        )
    }
}
