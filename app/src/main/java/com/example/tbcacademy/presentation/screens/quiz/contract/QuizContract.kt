package com.example.tbcacademy.presentation.screens.quiz.contract

import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.tbcacademy.presentation.screens.quiz.model.QuizStep
import com.example.tbcacademy.presentation.screens.quiz.model.SelectionType

data class QuizState(
    val currentStep: QuizStep = QuizStep.NAME,
    val name: String = "",
    val questions: List<QuestionItem> = emptyList(),
    val selectionType: SelectionType = SelectionType.MULTI
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
}

sealed interface QuizSideEffect {
    object NavigateToDashboard : QuizSideEffect
}