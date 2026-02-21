package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.DashboardNavKey
import com.example.api.QuizNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.quiz.screen.QuizScreen

fun EntryProviderScope<NavKey>.quizEntry(navigator: Navigator) {
    entry<QuizNavKey.QuizKey> {
        QuizScreen(
            onNavigateToDashboard = {
                navigator.navigate(DashboardNavKey.HomeNavKey)
            }
        )
    }
}