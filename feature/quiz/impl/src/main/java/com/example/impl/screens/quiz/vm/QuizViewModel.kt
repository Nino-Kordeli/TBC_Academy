package com.example.impl.screens.quiz.vm

import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizSideEffect
import com.example.impl.screens.quiz.contract.QuizState
import com.example.impl.screens.quiz.model.QuestionItem
import com.example.impl.screens.quiz.model.QuizStep
import com.example.impl.screens.quiz.model.SelectionType
import com.example.ui.base.BaseViewModel
import javax.inject.Inject

class QuizViewModel @Inject constructor() :
    BaseViewModel<QuizState, QuizEvent, QuizSideEffect>(
        initialState = loadStep(QuizStep.GENDER_AGE)
    ) {

    override fun onEvent(event: QuizEvent) {
        when (event) {

            is QuizEvent.NameChanged -> updateState { it.copy(name = event.value) }

            is QuizEvent.OnQuestionChecked -> updateState { state ->
                when (state.selectionType) {
                    SelectionType.MULTI -> state.copy(
                        questions = state.questions.map {
                            if (it.id == event.id) it.copy(selected = event.checked)
                            else it
                        }
                    )

                    SelectionType.SINGLE -> {
                        val selected = state.questions.first { it.id == event.id }
                        state.copy(
                            gender = if (state.currentStep == QuizStep.GENDER_AGE)
                                selected.primaryText.lowercase()
                            else state.gender,
                            questions = state.questions.map { it.copy(selected = it.id == event.id) }
                        )
                    }
                }
            }

            QuizEvent.NextClicked -> moveStep(1)

            QuizEvent.BackClicked -> moveStep(-1)

            is QuizEvent.HeightChanged -> updateState { it.copy(height = event.value) }

            is QuizEvent.WeightChanged -> updateState { it.copy(weight = event.value) }

            is QuizEvent.GoalWeightChanged -> updateState { it.copy(goalWeight = event.value) }
        }
    }

    private fun moveStep(delta: Int) {
        val steps = QuizStep.entries
        val index = steps.indexOf(state.value.currentStep)
        val nextIndex = index + delta

        if (nextIndex in steps.indices) {
            val nextStep = steps[nextIndex]

            val calculatedCalories = if (nextStep == QuizStep.CREATE_ACCOUNT) {
                val weight = state.value.weight.toDoubleOrNull() ?: 0.0
                val height = state.value.height.toDoubleOrNull() ?: 0.0
                val age = state.value.age ?: 25
                val gender = state.value.gender ?: "female"
                val activityMultiplier = 1.55
                val weeklyGoalKg = 0.5

                calculateCalories(
                    weight = weight,
                    height = height,
                    age = age,
                    gender = gender,
                    activityMultiplier = activityMultiplier,
                    weeklyGoalKg = weeklyGoalKg
                )
            } else {
                state.value.calculatedCalories
            }

            updateState {
                loadStep(nextStep).copy(
                    name = state.value.name,
                    gender = state.value.gender,
                    age = state.value.age,
                    height = state.value.height,
                    weight = state.value.weight,
                    goals = state.value.goals,
                    calculatedCalories = calculatedCalories
                )
            }
        } else {
            val calories = state.value.calculatedCalories ?: 0
            emitSideEffect(
                QuizSideEffect.NavigateToDashboard(calories)
            )
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
                QuestionItem(7, "I did not experience barriers")
            )
        )

        QuizStep.ACTIVITIES -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = listOf(
                QuestionItem(1, "Not Very Active", "Mostly sitting all day (e.g., desk job)"),
                QuestionItem(2, "Lightly Active", "On your feet part of the day (e.g., teacher)"),
                QuestionItem(3, "Active", "Some physical activity all day (e.g., food server)"),
                QuestionItem(4, "Very Active", "Heavy physical activity all day (e.g., carpenter)")
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

        QuizStep.NAME, QuizStep.BODY -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = emptyList()
        )

        QuizStep.WEEKLY_GOAL -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = listOf(
                QuestionItem(1, "Lose 0.25 kg per week", "Recommended"),
                QuestionItem(2, "Lose 0.50 kg per week"),
                QuestionItem(3, "Lose 0.75 kg per week"),
                QuestionItem(4, "Lose 1 kg per week")
            )
        )

        QuizStep.CREATE_ACCOUNT -> QuizState(
            currentStep = step,
            selectionType = SelectionType.SINGLE,
            questions = emptyList()
        )
    }
}

fun calculateCalories(
    weight: Double,
    height: Double,
    age: Int,
    gender: String,
    activityMultiplier: Double,
    weeklyGoalKg: Double
): Int {
    val bmr = if (gender.lowercase() == "male") {
        10 * weight + 6.25 * height - 5 * age + 5
    } else {
        10 * weight + 6.25 * height - 5 * age - 161
    }

    val maintenanceCalories = bmr * activityMultiplier
    val dailyAdjustment = (weeklyGoalKg * 7700) / 7
    return (maintenanceCalories - dailyAdjustment).toInt()
}
