package com.example.tbcacademy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.screen.feed.FeedScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.FEED,
        modifier = modifier
    ) {
        composable(Routes.FEED) {
            FeedScreen(navigator = navController)
        }
    }
}