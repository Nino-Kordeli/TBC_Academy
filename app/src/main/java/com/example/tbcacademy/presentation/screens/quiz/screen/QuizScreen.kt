package com.example.tbcacademy.presentation.screens.quiz.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.navigation.Routes
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizSideEffect
import com.example.tbcacademy.presentation.screens.quiz.model.QuizStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.AboutYourselfStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.ActivityLevelStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.BodyInfoStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.GoalsStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.NameStep
import com.example.tbcacademy.presentation.screens.quiz.screen.steps.WeeklyGoalStep
import com.example.tbcacademy.presentation.screens.quiz.vm.QuizViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                QuizSideEffect.NavigateToDashboard -> {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.QUIZ) { inclusive = true }
                    }
                }
            }
        }
    ) { state, onEvent ->
        when (state.currentStep) {
            QuizStep.NAME -> NameStep(state, onEvent)
            QuizStep.GOALS -> GoalsStep(state, onEvent)
            QuizStep.ACTIVITIES -> ActivityLevelStep(state, onEvent)
            QuizStep.GENDER_AGE -> AboutYourselfStep(state, onEvent)
            QuizStep.BODY -> BodyInfoStep(state, onEvent)
            QuizStep.WEEKLY_GOAL -> WeeklyGoalStep(state, onEvent)
            else -> {}
        }
    }

}

@Composable
@Preview
fun QuizScreenPreview() {
    QuizScreen()
}