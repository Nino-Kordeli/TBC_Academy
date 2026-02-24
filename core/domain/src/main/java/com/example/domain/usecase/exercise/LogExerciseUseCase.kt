package com.example.domain.usecase.exercise

import com.example.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class LogExerciseUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(caloriesBurnt: Int) {
        userPreferencesRepository.addExerciseCalories(caloriesBurnt)
    }
}