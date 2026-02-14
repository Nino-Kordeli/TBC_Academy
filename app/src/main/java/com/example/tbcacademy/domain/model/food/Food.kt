package com.example.tbcacademy.domain.model.food

data class Food(
    val id: String,
    val name: String,
    val calories: Int,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val isMeal: Boolean
)