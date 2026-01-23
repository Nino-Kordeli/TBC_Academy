package com.example.tbcacademy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.orders.screen.OrdersScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ORDERS,
        modifier = modifier
    ) {
        composable(Routes.ORDERS) {
            OrdersScreen(navigator = navController)
        }
    }
}