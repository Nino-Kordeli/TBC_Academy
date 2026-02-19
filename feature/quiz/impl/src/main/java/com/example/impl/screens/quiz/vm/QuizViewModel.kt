package com.example.impl.screens.quiz.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizSideEffect
import com.example.impl.screens.quiz.contract.QuizState
import com.example.impl.screens.quiz.model.QuestionItem
import com.example.impl.screens.quiz.model.QuizStep
import com.example.impl.screens.quiz.model.SelectionType
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<QuizState, QuizEvent, QuizSideEffect>(
    initialState = QuizState(
        currentStep = QuizStep.NAME,
        selectionType = SelectionType.SINGLE,
        questions = emptyList()
    )
) {
    override fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.NameChanged ->
                updateState { it.copy(name = event.value) }

            is QuizEvent.OnQuestionChecked -> updateState { state ->
                when (state.selectionType) {
                    SelectionType.MULTI -> state.copy(
                        questions = state.questions.map {
                            if (it.id == event.id) it.copy(selected = event.checked)
                            else it
                        }
                    )

                    SelectionType.SINGLE -> {
                        val selected = state.questions.firstOrNull { it.id == event.id }
                        state.copy(
                            gender = if (state.currentStep == QuizStep.GENDER_AGE && selected != null)
                                selected.primaryText.lowercase()
                            else state.gender,
                            weeklyGoalKg = if (state.currentStep == QuizStep.WEEKLY_GOAL)
                                when (event.id) {
                                    1 -> 0.25
                                    2 -> 0.50
                                    3 -> 0.75
                                    4 -> 1.0
                                    else -> 0.5
                                }
                            else state.weeklyGoalKg,
                            activityMultiplier = if (state.currentStep == QuizStep.ACTIVITIES)
                                when (event.id) {
                                    1 -> 1.2
                                    2 -> 1.375
                                    3 -> 1.55
                                    4 -> 1.725
                                    else -> 1.55
                                }
                            else state.activityMultiplier,
                            questions = state.questions.map {
                                it.copy(selected = it.id == event.id)
                            },
                            errorMessage = null
                        )
                    }
                }
            }

            QuizEvent.NextClicked -> {
                validateAndMoveNext()
            }

            QuizEvent.BackClicked -> moveStep(-1)
            is QuizEvent.HeightChanged -> updateState { it.copy(height = event.value) }
            is QuizEvent.WeightChanged -> updateState { it.copy(weight = event.value) }
            is QuizEvent.GoalWeightChanged -> updateState { it.copy(goalWeight = event.value) }
            else -> {}
        }
    }

    private fun validateAndMoveNext() {
        val currentState = state.value

        val validationError = when (currentState.currentStep) {
            QuizStep.NAME -> {
                when {
                    currentState.name.isBlank() -> "Please enter your name"
                    currentState.name.length < 2 -> "Name must be at least 2 characters"
                    else -> null
                }
            }

            QuizStep.BODY -> {
                val height = currentState.height.toDoubleOrNull()
                val weight = currentState.weight.toDoubleOrNull()
                val goalWeight = currentState.goalWeight.toDoubleOrNull()

                when {
                    currentState.height.isBlank() -> "Please enter your height"
                    currentState.weight.isBlank() -> "Please enter your current weight"
                    currentState.goalWeight.isBlank() -> "Please enter your goal weight"
                    height == null || height <= 0 -> "Please enter a valid height"
                    weight == null || weight <= 0 -> "Please enter a valid weight"
                    goalWeight == null || goalWeight <= 0 -> "Please enter a valid goal weight"
                    height !in 100.0..250.0 -> "Height must be between 100-250 cm"
                    weight !in 30.0..300.0 -> "Weight must be between 30-300 kg"
                    goalWeight !in 30.0..300.0 -> "Goal weight must be between 30-300 kg"
                    goalWeight >= weight -> "Goal weight must be less than current weight"
                    weight - goalWeight < 1 -> "Weight difference should be at least 1 kg"
                    weight - goalWeight > 50 -> "We recommend losing weight gradually. Please set a more realistic goal (max 50kg difference)"
                    goalWeight < 40 && currentState.gender == "female" -> "Goal weight seems too low for healthy weight range"
                    goalWeight < 50 && currentState.gender == "male" -> "Goal weight seems too low for healthy weight range"
                    else -> null
                }
            }

            QuizStep.GOALS, QuizStep.ACTIVITIES, QuizStep.GENDER_AGE, QuizStep.WEEKLY_GOAL -> {
                if (currentState.questions.any { it.selected }) null
                else "Please select an option to continue"
            }

            QuizStep.CREATE_ACCOUNT -> null
        }

        if (validationError != null) {
            updateState { it.copy(errorMessage = validationError) }
        } else {
            moveStep(1)
        }
    }

    private fun moveStep(delta: Int) {
        val steps = QuizStep.entries
        val index = steps.indexOf(state.value.currentStep)
        val nextIndex = index + delta

        if (nextIndex in steps.indices) {
            val nextStep = steps[nextIndex]

            val calculatedCalories = if (nextStep == QuizStep.CREATE_ACCOUNT) {
                calculateCalories(
                    weight = state.value.weight.toDoubleOrNull() ?: 70.0,
                    height = state.value.height.toDoubleOrNull() ?: 170.0,
                    age = state.value.age ?: 25,
                    gender = state.value.gender ?: "female",
                    activityMultiplier = state.value.activityMultiplier,
                    weeklyGoalKg = state.value.weeklyGoalKg
                )
            } else {
                state.value.calculatedCalories
            }

            val newQuestions = getQuestionsForStep(nextStep)
            val newSelectionType = getSelectionTypeForStep(nextStep)

            updateState {
                it.copy(
                    currentStep = nextStep,
                    questions = newQuestions,
                    selectionType = newSelectionType,
                    calculatedCalories = calculatedCalories
                )
            }
        } else {
            // Quiz finished
            val calories = state.value.calculatedCalories ?: 2000
            viewModelScope.launch {
                userPreferencesRepository.saveGoalCalories(calories)
                userPreferencesRepository.saveUserProfile(
                    email = "",
                    name = state.value.name,
                    weight = state.value.weight,
                    height = state.value.height,
                    age = state.value.age,
                    gender = state.value.gender
                )
            }
            emitSideEffect(QuizSideEffect.NavigateToDashboard(calories))
        }
    }

    private fun getQuestionsForStep(step: QuizStep): List<QuestionItem> {
        return when (step) {
            QuizStep.GOALS -> listOf(
                QuestionItem(1, "Food cravings"),
                QuestionItem(2, "Healthy food doesn't taste good"),
                QuestionItem(3, "Holidays/Vacations/Social events"),
                QuestionItem(4, "Healthy food is too expensive"),
                QuestionItem(5, "I can't cook"),
                QuestionItem(6, "Lack of progress"),
                QuestionItem(7, "I did not experience barriers")
            )

            QuizStep.ACTIVITIES -> listOf(
                QuestionItem(1, "Not Very Active", "Mostly sitting all day"),
                QuestionItem(2, "Lightly Active", "On your feet part of the day"),
                QuestionItem(3, "Active", "Some physical activity all day"),
                QuestionItem(4, "Very Active", "Heavy physical activity all day")
            )

            QuizStep.GENDER_AGE -> listOf(
                QuestionItem(1, "Male"),
                QuestionItem(2, "Female")
            )

            QuizStep.WEEKLY_GOAL -> listOf(
                QuestionItem(1, "Lose 0.25 kg per week", "Recommended"),
                QuestionItem(2, "Lose 0.50 kg per week"),
                QuestionItem(3, "Lose 0.75 kg per week"),
                QuestionItem(4, "Lose 1 kg per week")
            )

            QuizStep.NAME, QuizStep.BODY, QuizStep.CREATE_ACCOUNT -> emptyList()
        }
    }

    private fun getSelectionTypeForStep(step: QuizStep): SelectionType {
        return when (step) {
            QuizStep.GOALS -> SelectionType.MULTI
            else -> SelectionType.SINGLE
        }
    }

    private fun calculateCalories(
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
}