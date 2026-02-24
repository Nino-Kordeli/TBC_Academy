package com.example.domain.model.workout

data class Exercise(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val equipment: String,
    val caloriesBurned: Int,
    val imageUrl: String
)