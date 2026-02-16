package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import com.example.api.AuthenticationNavKey
import com.example.core.navigation.Navigator
import com.example.core.navigation.rememberNavigationState
import com.example.core.navigation.toEntries
import com.example.designsystem.theme.ComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation3.ui.NavDisplay
import com.example.impl.navigation.dashboardEntry
import com.example.impl.navigation.loginEntry
import com.example.impl.navigation.quizEntry
import com.example.impl.navigation.registerEntry
import com.example.impl.navigation.welcomeEntry

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            ComposeAppTheme {
//                AppNavigation()
//                val navigationState = rememberNavigationState(
//                    startKey = WelcomeNavKey,
//                    topLevelKeys = setOf(WelcomeNavKey)
//                )
//
//                val navigator = remember(navigationState) { Navigator(navigationState) }
//
//                val navController = rememberNavController()
//
//                Scaffold(
//                    topBar = {
//                        val currentRoute =
//                            navController.currentBackStackEntryAsState().value?.destination?.route
//
//                        if (currentRoute == Routes.QUIZ) {
//                            val quizViewModel: QuizViewModel = hiltViewModel()
//                            val state by quizViewModel.state.collectAsStateWithLifecycle()
//
//                            /*if (!state.lastStep) {
//                                QuizTopProgressBar(progress = state.progress)
//                            }*/
//                        }
//                    },
//                    bottomBar = {
//                        val currentRoute =
//                            navController.currentBackStackEntryAsState().value?.destination?.route
//
//                        val bottomBarVisible = listOf(
//                            Routes.DASHBOARD
//                        )
//
//                        if (currentRoute?.startsWith(Routes.DASHBOARD) == true){
//                            BottomBar(
//                                navController = navController,
//                                hasSearch = currentRoute.startsWith(Routes.DASHBOARD)
//                            )
//                        }
//                    }
//                )
//                { padding ->
//                    AppNavHost(
//                        navController = navController,
//                        modifier = Modifier.padding(padding)
//                    )
//                }
//
//            }
//        }
//    }
//}

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
    val navigationState = rememberNavigationState(
        startKey = AuthenticationNavKey.WelcomeNavKey,
        topLevelKeys = setOf(AuthenticationNavKey.WelcomeNavKey)
    )

    val navigator = remember(navigationState) { Navigator(navigationState) }

    val entryProvider = entryProvider {
        welcomeEntry(navigator)
        loginEntry(navigator)
        quizEntry(navigator)
        registerEntry(navigator)
        dashboardEntry(navigator)
    }

    val entries = navigationState.toEntries(entryProvider)

    Scaffold { padding ->
        NavDisplay(
            entries = entries,
            modifier = Modifier.padding(padding),
            onBack = { navigator.goBack() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {}
}