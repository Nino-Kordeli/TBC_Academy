package com.example.impl.screens.quiz.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.designsystem.theme.WhiteBlue
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
    onNavigateToDashboard: (Int) -> Unit
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            QuizBottomBar(
                currentStep = state.currentStep,
                isLastStep = state.isLastStep,
                onNext = { viewModel.onEvent(QuizEvent.NextClicked) },
                onBack = { viewModel.onEvent(QuizEvent.BackClicked) }
            )
        }
    ) { padding ->
        BaseScreen(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            onSideEffect = { effect ->
                when (effect) {
                    is QuizSideEffect.NavigateToDashboard -> {
                        onNavigateToDashboard(effect.calories)
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

@Composable
fun QuizBottomBar(
    currentStep: QuizStep,
    isLastStep: Boolean,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        color = White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep != QuizStep.NAME) {
                Button(
                    onClick = onBack,
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhiteBlue
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_blue),
                        contentDescription = "Back",
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Text(if (isLastStep) "Finish" else "Next")
            }
        }
    }
}