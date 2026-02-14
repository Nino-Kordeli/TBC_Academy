package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.navigation.AppNavHost
import com.example.tbcacademy.presentation.navigation.Routes
import com.example.tbcacademy.presentation.screens.quiz.vm.QuizViewModel
import com.example.tbcacademy.presentation.screens.dashboard.screen.BottomBar
import com.example.tbcacademy.presentation.theme.ComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeAppTheme {

                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route

                        if (currentRoute == Routes.QUIZ) {
                            val quizViewModel: QuizViewModel = hiltViewModel()
                            val state by quizViewModel.state.collectAsStateWithLifecycle()

                            /*if (!state.lastStep) {
                                QuizTopProgressBar(progress = state.progress)
                            }*/
                        }
                    },
                    bottomBar = {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route

                        val bottomBarVisible = listOf(
                            Routes.DASHBOARD
                        )

                        if (currentRoute?.startsWith(Routes.DASHBOARD) == true){
                            BottomBar(
                                navController = navController,
                                hasSearch = currentRoute.startsWith(Routes.DASHBOARD)
                            )
                        }
                    }
                )
                { padding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {}
}