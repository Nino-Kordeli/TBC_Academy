package com.example.impl.screens.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.WorkoutNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.screen.WorkoutScreen

fun EntryProviderScope<NavKey>.splashEntry(navigator: Navigator) {
    entry<WorkoutNavKey> {
        WorkoutScreen()
    }
}