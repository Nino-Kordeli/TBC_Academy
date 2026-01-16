package com.example.tbcacademy.presentation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.presentation.login.screen.LoginScreen
import com.example.tbcacademy.presentation.presentation.register.screen.RegisterScreen
import com.example.tbcacademy.presentation.presentation.welcome.screen.WelcomeScreen
import com.example.tbcacademy.presentation.presentation.profile.screen.ProfileScreen

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

        composable(Routes.WELCOME) {
            WelcomeScreen(navigator = navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navigator = navController)
        }

        composable(Routes.REGISTER) {
            RegisterScreen(navigator = navController)
        }

        composable(Routes.PROFILE){
            ProfileScreen(navigator = navController)
        }
    }
}
