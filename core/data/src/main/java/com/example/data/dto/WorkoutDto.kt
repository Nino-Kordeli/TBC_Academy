package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutResponseDto(
    val categories: List<WorkoutCategoryDto>
)

@Serializable
data class WorkoutCategoryDto(
    val id: String,
    val title: String,
    val description: String,
    val exercises: List<ExerciseDto>
)

@Serializable
data class ExerciseDto(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val equipment: String,
    val caloriesBurned: Int,
    val imageUrl: String
)