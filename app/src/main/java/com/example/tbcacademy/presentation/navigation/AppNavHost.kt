package com.example.tbcacademy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.screens.dashboard.screen.DashboardScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME,
        modifier = modifier
    ) {

        composable(Routes.DASHBOARD) {
            DashboardScreen(navigator = navController)
        }
    }
}
