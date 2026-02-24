package com.example.impl.screens.quiz.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.White
import com.example.impl.screens.quiz.components.QuizBottomBar
import com.example.impl.screens.quiz.components.QuizProgressBar
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizSideEffect
import com.example.impl.screens.quiz.model.QuizStep
import com.example.impl.screens.quiz.screen.steps.AboutYourselfStep
import com.example.impl.screens.quiz.screen.steps.AccountCreatedStep
import com.example.impl.screens.quiz.screen.steps.ActivityLevelStep
import com.example.impl.screens.quiz.screen.steps.BodyInfoStep
import com.example.impl.screens.quiz.screen.steps.GoalsStep
import com.example.impl.screens.quiz.screen.steps.NameStep
import com.example.impl.screens.quiz.screen.steps.WeeklyGoalStep
import com.example.impl.screens.quiz.vm.QuizViewModel
import com.example.ui.base.BaseScreen

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.onEvent(QuizEvent.DismissError)
        }
    }

    BackHandler() {}

    Column(modifier = Modifier.fillMaxWidth()) {
        QuizProgressBar(
            progress = state.progress
        )
    }

    Scaffold(
        topBar = {
            QuizProgressBar(progress = state.progress)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            QuizBottomBar(
                currentStep = state.currentStep,
                isLastStep = state.isLastStep,
                onNext = { viewModel.onEvent(QuizEvent.NextClicked) },
                onBack = { viewModel.onEvent(QuizEvent.BackClicked) }
            )
        }
    ) { _ ->
        BaseScreen(
            modifier = Modifier.padding(23.dp),
            viewModel = viewModel,
            onSideEffect = { effect ->
                when (effect) {
                    is QuizSideEffect.NavigateToDashboard -> {
                        onNavigateToDashboard()
                    }
                }
            }
        ) { _, onEvent ->
            when (state.currentStep) {
                QuizStep.NAME -> NameStep(state, onEvent)
                QuizStep.GOALS -> GoalsStep(state, onEvent)
                QuizStep.ACTIVITIES -> ActivityLevelStep(state, onEvent)
                QuizStep.GENDER_AGE -> AboutYourselfStep(state, onEvent)
                QuizStep.BODY -> BodyInfoStep(state, onEvent)
                QuizStep.WEEKLY_GOAL -> WeeklyGoalStep(state, onEvent)
                QuizStep.CREATE_ACCOUNT -> AccountCreatedStep(state.calculatedCalories)
            }
        }
    }
}
