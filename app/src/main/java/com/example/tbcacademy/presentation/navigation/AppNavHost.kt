package com.example.tbcacademy.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.screens.quiz.screen.QuizScreen
import com.example.tbcacademy.presentation.screens.dashboard.screen.DashboardScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD,
        modifier = modifier
    ) {

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                navigator = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(Routes.QUIZ){
            QuizScreen()
        }
    }
}
