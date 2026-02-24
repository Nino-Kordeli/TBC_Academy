package com.example.domain.usecase.workout

import com.example.domain.repository.workout.WorkoutRepository
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke() = repository.getWorkouts()
}
