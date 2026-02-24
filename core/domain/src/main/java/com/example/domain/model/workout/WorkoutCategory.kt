package com.example.domain.model.workout

data class WorkoutCategory(
    val id: String,
    val title: String,
    val description: String,
    val exercises: List<Exercise>
)