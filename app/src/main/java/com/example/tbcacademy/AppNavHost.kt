package com.example.tbcacademy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.core.presentation.navigation.Routes
import com.example.tbcacademy.feature.register.screen.RegisterScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.REGISTER,
        modifier = modifier
    ) {

        composable(Routes.REGISTER) {
            RegisterScreen(navigator = navController)
        }
    }
}