package com.example.tbcacademy.presentation.screens.quiz.contract

import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.tbcacademy.presentation.screens.quiz.model.QuizStep
import com.example.tbcacademy.presentation.screens.quiz.model.SelectionType

data class QuizState(
    val currentStep: QuizStep = QuizStep.NAME,
    val name: String = "",
    val gender: String? = null,
    val age: Int? = null,
    val height: String = "",
    val weight: String = "",
    val goalWeight: String = "",
    val activityLevel: SelectionType = SelectionType.MULTI,
    val goals: List<String> = emptyList(),
    val questions: List<QuestionItem> = emptyList(),
    val selectionType: SelectionType = SelectionType.SINGLE,
    val dailyCalories: Int = 0,
    val calculatedCalories: Int? = null
) {
    val progress: Float
        get() {
            val steps = QuizStep.entries
            return (steps.indexOf(currentStep) + 1) / steps.size.toFloat()
        }

    val isLastStep: Boolean
        get() = currentStep == QuizStep.CREATE_ACCOUNT
}


sealed interface QuizEvent {
    data class NameChanged(val value: String) : QuizEvent
    object NextClicked : QuizEvent
    object BackClicked : QuizEvent
    data class OnQuestionChecked(
        val id: Int,
        val checked: Boolean
    ) : QuizEvent

    data class HeightChanged(val value: String) : QuizEvent
    data class WeightChanged(val value: String) : QuizEvent
    data class GoalWeightChanged(val value: String) : QuizEvent
}

sealed interface QuizSideEffect {
    data class NavigateToDashboard(val calories: Int) : QuizSideEffect
}