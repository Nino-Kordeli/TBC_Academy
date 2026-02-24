package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.api.AuthenticationNavKey
import com.example.api.DashboardNavKey
import com.example.api.DiaryNavKey
import com.example.api.ProfileNavKey
import com.example.api.QuizNavKey
import com.example.api.RecipesNavKey
import com.example.api.SplashNavKey
import com.example.api.WorkoutNavKey
import com.example.core.navigation.Navigator
import com.example.core.navigation.rememberNavigationState
import com.example.core.navigation.toEntries
import com.example.designsystem.theme.ComposeAppTheme
import com.example.impl.navigation.addFoodDetailsEntry
import com.example.impl.navigation.addFoodEntry
import com.example.impl.navigation.diaryEntry
import com.example.impl.navigation.homeEntry
import com.example.impl.navigation.loginEntry
import com.example.impl.navigation.profileEntry
import com.example.impl.navigation.quizEntry
import com.example.impl.navigation.recipesEntry
import com.example.impl.navigation.registerEntry
import com.example.impl.navigation.searchNavEntry
import com.example.impl.navigation.splashEntry
import com.example.impl.navigation.welcomeEntry
import com.example.impl.navigation.workoutEntry
import com.example.ui.components.CustomSnackbar
import com.example.ui.components.bottom_bar.BottomBar
import com.example.ui.components.bottom_bar.BottomBarDestination
import com.example.ui.snackbar.SnackbarController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val navigationState = rememberNavigationState(
        startKey = SplashNavKey,
        topLevelKeys = setOf(
            AuthenticationNavKey.WelcomeNavKey,
            QuizNavKey.QuizKey,
            DashboardNavKey.HomeNavKey,
            DashboardNavKey.SearchNavKey,
            DiaryNavKey.DiaryNavKey,
            SplashNavKey,
            ProfileNavKey.ProfileNavKey,
            WorkoutNavKey.WorkoutNavKey,
            RecipesNavKey.RecipesNavKey
            //AddFoodDetailNavKey//es unda wavshalo ro imushaos
        )
    )

    val navigator = remember(navigationState) { Navigator(navigationState) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackbarController = remember(snackbarHostState, scope) {
        SnackbarController(snackbarHostState, scope)
    }

    val entryProvider = entryProvider {
        welcomeEntry(navigator)
        loginEntry(navigator, snackbarController)
        registerEntry(navigator, snackbarController)
        quizEntry(navigator)
        homeEntry(navigator)
        diaryEntry(navigator)
        searchNavEntry(navigator)
        addFoodEntry(navigator)
        addFoodDetailsEntry(navigator)
        splashEntry(navigator)
        profileEntry(navigator)
        workoutEntry(navigator)
        recipesEntry(navigator)
    }

    val entries = navigationState.toEntries(entryProvider)

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {

            val currentKey = navigationState.currentKey

            if (
                currentKey is DashboardNavKey.HomeNavKey ||
                currentKey is ProfileNavKey.ProfileNavKey ||
                currentKey is DashboardNavKey.SearchNavKey ||
                currentKey is DiaryNavKey.DiaryNavKey
            ) {

                val currentDestination = when (currentKey) {
                    is DashboardNavKey.HomeNavKey -> BottomBarDestination.Home
                    is DiaryNavKey.DiaryNavKey -> BottomBarDestination.Diary
                    is ProfileNavKey.ProfileNavKey -> BottomBarDestination.Profile
                    is DashboardNavKey.SearchNavKey -> BottomBarDestination.Search
                    else -> BottomBarDestination.Home
                }

                BottomBar(
                    currentDestination = currentDestination,
                    navigator = { destination ->
                        when (destination) {
                            BottomBarDestination.Home -> navigator.navigate(
                                DashboardNavKey.HomeNavKey
                            )

                            BottomBarDestination.Diary -> navigator.navigate(DiaryNavKey.DiaryNavKey)
                            BottomBarDestination.Profile -> navigator.navigate(ProfileNavKey.ProfileNavKey)
                            BottomBarDestination.Search -> navigator.navigate(DashboardNavKey.SearchNavKey)
                        }
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                CustomSnackbar(
                    snackbarData = data,
                    isError = data.visuals.message.contains("error", ignoreCase = true) ||
                            data.visuals.message.contains("failed", ignoreCase = true)
                )
            }
        }
    ) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            entries = entries,
            onBack = { navigator.goBack() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {}
}