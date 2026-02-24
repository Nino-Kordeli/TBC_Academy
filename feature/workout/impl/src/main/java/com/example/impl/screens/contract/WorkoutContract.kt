package com.example.impl.screens.contract

import com.example.domain.model.workout.WorkoutCategory

data class WorkoutState(
    val categories: List<WorkoutCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val loggedExerciseIds: Set<String> = emptySet() // tracks tapped exercises
)

sealed interface WorkoutEvent {
    data class LogExercise(val exerciseId: String, val calories: Int) : WorkoutEvent
}

sealed interface WorkoutSideEffect {
    data class ShowToast(val message: String) : WorkoutSideEffect
}