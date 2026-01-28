package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.screen.cards.CardsScreen
import com.example.tbcacademy.presentation.screen.vm.CardsViewModel
import com.example.tbcacademy.presentation.theme.ComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeAppTheme {

                val viewModel: CardsViewModel = hiltViewModel()

                BaseScreen(
                    viewModel = viewModel
                ) { state, onEvent ->

                    var isDarkTheme by remember { mutableStateOf(false) }
                    CardsScreen(
                        state = state,
                        onEvent = onEvent,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme }
                    )
                }
            }
        }
    }
}