package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.work.HiltWorkerFactory
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.api.AuthenticationNavKey
import com.example.core.navigation.Navigator
import com.example.core.navigation.rememberNavigationState
import com.example.core.navigation.toEntries
import com.example.data.worker.DailyResetWorker
import com.example.designsystem.theme.ComposeAppTheme
import com.example.impl.navigation.dashboardEntry
import com.example.impl.navigation.loginEntry
import com.example.impl.navigation.quizEntry
import com.example.impl.navigation.registerEntry
import com.example.impl.navigation.welcomeEntry
import com.example.ui.components.CustomSnackbar
import com.example.ui.snackbar.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleDailyReset()

        setContent {
            ComposeAppTheme {
                AppNavigation()
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun scheduleDailyReset() {
        val constraints = Constraints.Builder().build()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(
            1, TimeUnit.DAYS
        ).setConstraints(constraints).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reset_work",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }
}

@Composable
private fun AppNavigation() {
    val navigationState = rememberNavigationState(
        startKey = AuthenticationNavKey.WelcomeNavKey,
        topLevelKeys = setOf(AuthenticationNavKey.WelcomeNavKey)
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
        dashboardEntry(navigator)
    }

    val entries = navigationState.toEntries(entryProvider)

    Scaffold(
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