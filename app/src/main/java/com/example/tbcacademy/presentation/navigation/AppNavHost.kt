package com.example.tbcacademy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.login.screen.LoginScreen
import com.example.tbcacademy.presentation.products.ProductsScreen
import com.example.tbcacademy.presentation.profile.screen.ProfileScreen
import com.example.tbcacademy.presentation.register.screen.RegisterScreen
import com.example.tbcacademy.presentation.welcome.screen.WelcomeScreen

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

        composable(Routes.PROFILE) {
            ProfileScreen(navigator = navController)
        }

        composable(Routes.CATALOGUE) {
            ProductsScreen(navigator = navController)
        }
    }
}
