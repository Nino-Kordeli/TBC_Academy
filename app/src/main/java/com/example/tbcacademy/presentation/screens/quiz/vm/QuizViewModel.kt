package com.example.tbcacademy.presentation.screens.quiz.vm

import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizSideEffect
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.tbcacademy.presentation.screens.quiz.model.QuizStep
import com.example.tbcacademy.presentation.screens.quiz.model.SelectionType
import javax.inject.Inject

class QuizViewModel @Inject constructor() :
    BaseViewModel<QuizState, QuizEvent, QuizSideEffect>(
        initialState = loadStep(QuizStep.GENDER_AGE)
    ) {

    override fun onEvent(event: QuizEvent) {
        when (event) {

            is QuizEvent.NameChanged -> {
                updateState { it.copy(name = event.value) }
            }

            is QuizEvent.OnQuestionChecked -> {
                updateState { state ->
                    when (state.selectionType) {

                        SelectionType.MULTI ->
                            state.copy(
                                questions = state.questions.map {
                                    if (it.id == event.id)
                                        it.copy(selected = event.checked)
                                    else it
                                }
                            )

                        SelectionType.SINGLE ->
                            state.copy(
                                questions = state.questions.map {
                                    it.copy(selected = it.id == event.id)
                                }
                            )
                    }
                }
            }

            QuizEvent.NextClicked -> moveStep(1)
            QuizEvent.BackClicked -> moveStep(-1)
        }
    }

    private fun moveStep(delta: Int) {
        val steps = QuizStep.entries
        val index = steps.indexOf(state.value.currentStep)
        val nextIndex = index + delta

        if (nextIndex in steps.indices) {
            updateState({ loadStep(steps[nextIndex]) })
        } else {
            emitSideEffect(QuizSideEffect.NavigateToDashboard)
        }
    }
}


private fun loadStep(step: QuizStep): QuizState {
    return when (step) {
        QuizStep.GOALS -> QuizState(
            currentStep = step,
            selectionType = SelectionType.MULTI,
            questions = listOf(
                QuestionItem(1, "Food cravings"),
                QuestionItem(2, "Healthy food doesn't taste good"),
                QuestionItem(3, "Holidays/Vacations/Social events"),
                QuestionItem(4, "Healthy food is too expensive"),
                QuestionItem(5, "I can't cook"),
                QuestionItem(6, "Lack of progress"),
                QuestionItem(7, "I did not experience barriers"),
            )
        )

        QuizStep.ACTIVITIES -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = listOf(
                QuestionItem(
                    1,
                    "Not Very Active",
                    "Spend most of the day sitting (e.g., bank teller ,desk job).",
                ),
                QuestionItem(
                    2,
                    "Lightly Active",
                    "Spend a good part of the day on your feet (e.g.. teacher, salesperson)"
                ),
                QuestionItem(
                    3,
                    "Active",
                    "Spend a good part of the day doing some physical activity (e.g.. food server, carrier)"
                ), QuestionItem(
                    3,
                    "Very Active",
                    "Spend a good part of the day doing heavy physical activity (e.g.. bike messenger, carpenter)"
                )
            )
        )

        QuizStep.GENDER_AGE -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = listOf(
                QuestionItem(1, "Male"),
                QuestionItem(2, "Female")
            )
        )




        QuizStep.NAME -> TODO()
        QuizStep.BODY -> TODO()
        QuizStep.WEEKLY_GOAL -> TODO()
        QuizStep.CREATE_ACCOUNT -> TODO()
    }
}
