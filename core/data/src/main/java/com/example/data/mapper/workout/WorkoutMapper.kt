package com.example.data.mapper.workout

import com.example.data.dto.workout.ExerciseDto
import com.example.data.dto.workout.WorkoutCategoryDto
import com.example.data.dto.workout.WorkoutResponseDto
import com.example.domain.model.workout.Exercise
import com.example.domain.model.workout.WorkoutCategory

fun WorkoutResponseDto.toDomain() = this.categories.map { it.toDomain() }

private fun WorkoutCategoryDto.toDomain() = WorkoutCategory(
    id = id,
    title = title,
    description = description,
    exercises = exercises.map { it.toDomain() }
)

private fun ExerciseDto.toDomain() = Exercise(
    id = id,
    title = title,
    description = description,
    duration = duration,
    equipment = equipment,
    caloriesBurned = caloriesBurned,
    imageUrl = imageUrl
)
