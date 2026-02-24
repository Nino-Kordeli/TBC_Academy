package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.WorkoutNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.screen.WorkoutScreen

fun EntryProviderScope<NavKey>.workoutEntry(navigator: Navigator) {
    entry<WorkoutNavKey.WorkoutNavKey> {
        WorkoutScreen()
    }
}