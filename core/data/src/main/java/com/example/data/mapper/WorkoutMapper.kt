package com.example.data.mapper

import com.example.data.dto.ExerciseDto
import com.example.data.dto.WorkoutCategoryDto
import com.example.data.dto.WorkoutResponseDto
import com.example.domain.model.workout.Exercise
import com.example.domain.model.workout.WorkoutCategory

fun WorkoutCategoryDto.toDomain() = WorkoutCategory(
    id = id,
    title = title,
    description = description,
    exercises = exercises.map { it.toDomain() }
)

fun ExerciseDto.toDomain() = Exercise(
    id = id,
    title = title,
    description = description,
    duration = duration,
    equipment = equipment,
    caloriesBurned = caloriesBurned,
    imageUrl = imageUrl
)
