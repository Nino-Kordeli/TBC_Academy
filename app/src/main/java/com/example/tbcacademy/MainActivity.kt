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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.api.AddFoodDetailNavKey
import com.example.api.AuthenticationNavKey
import com.example.api.DashboardNavKey
import com.example.api.DiaryNavKey
import com.example.api.QuizNavKey
import com.example.core.navigation.Navigator
import com.example.core.navigation.rememberNavigationState
import com.example.core.navigation.toEntries
import com.example.data.di.AuthRepositoryEntryPoint
import com.example.designsystem.theme.ComposeAppTheme
import com.example.impl.navigation.addFoodDetailsEntry
import com.example.impl.navigation.addFoodEntry
import com.example.impl.navigation.diaryEntry
import com.example.impl.navigation.homeEntry
import com.example.impl.navigation.loginEntry
import com.example.impl.navigation.moreNavEntry
import com.example.impl.navigation.quizEntry
import com.example.impl.navigation.registerEntry
import com.example.impl.navigation.searchNavEntry
import com.example.impl.navigation.welcomeEntry
import com.example.ui.components.CustomSnackbar
import com.example.ui.components.bottom_bar.BottomBar
import com.example.ui.components.bottom_bar.BottomBarDestination
import com.example.ui.snackbar.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val context = LocalContext.current

    val authRepository = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                AuthRepositoryEntryPoint::class.java
            )
            .authRepository()
    }

    val startKey = remember {
        if (authRepository.isLoggedIn()) {
            DashboardNavKey.HomeNavKey
        } else {
            AuthenticationNavKey.WelcomeNavKey
        }
    }

    val navigationState = rememberNavigationState(
        startKey = startKey,
        topLevelKeys = setOf(
            AuthenticationNavKey.WelcomeNavKey,
            QuizNavKey.QuizKey,
            DashboardNavKey.HomeNavKey,
            DashboardNavKey.MoreNavKey,
            DashboardNavKey.SearchNavKey,
            DiaryNavKey.DiaryNavKey,
            AddFoodDetailNavKey
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
        moreNavEntry(navigator)
        searchNavEntry(navigator)
        addFoodEntry(navigator)
        addFoodDetailsEntry(navigator)
    }

    val entries = navigationState.toEntries(entryProvider)

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {

            val currentKey = navigationState.currentKey

            if (
                currentKey is DashboardNavKey.HomeNavKey ||
                currentKey is DashboardNavKey.MoreNavKey ||
                currentKey is DashboardNavKey.SearchNavKey ||
                currentKey is DiaryNavKey.DiaryNavKey
            ) {

                val currentDestination = when (currentKey) {
                    is DashboardNavKey.HomeNavKey -> BottomBarDestination.Home
                    is DiaryNavKey.DiaryNavKey -> BottomBarDestination.Diary
                    is DashboardNavKey.MoreNavKey -> BottomBarDestination.More
                    is DashboardNavKey.SearchNavKey -> BottomBarDestination.Search
                    else -> BottomBarDestination.Home
                }

                BottomBar(
                    currentDestination = currentDestination,
                    hasSearch = currentKey is DashboardNavKey.HomeNavKey,
                    navigator = { destination ->
                        when (destination) {
                            BottomBarDestination.Home -> navigator.navigate(
                                DashboardNavKey.HomeNavKey
                            )

                            BottomBarDestination.Diary -> navigator.navigate(DiaryNavKey.DiaryNavKey)
                            BottomBarDestination.More -> navigator.navigate(DashboardNavKey.MoreNavKey)
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