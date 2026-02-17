package com.example.impl.screens.quiz.contract

import com.example.impl.screens.quiz.model.QuestionItem
import com.example.impl.screens.quiz.model.QuizStep
import com.example.impl.screens.quiz.model.SelectionType

data class QuizState(
    val currentStep: QuizStep = QuizStep.NAME,
    val name: String = "",
    val gender: String? = null,
    val age: Int? = null,
    val height: String = "",
    val weight: String = "",
    val goalWeight: String = "",
    val goals: List<String> = emptyList(),
    val questions: List<QuestionItem> = emptyList(),
    val selectedAnswers: Map<QuizStep, Set<Int>> = emptyMap(),
    val selectionType: SelectionType = SelectionType.SINGLE,
    val calculatedCalories: Int? = null,
    val weeklyGoalKg: Double = 0.5,
    val activityMultiplier: Double = 1.55
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