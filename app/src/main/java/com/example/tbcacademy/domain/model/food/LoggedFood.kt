package com.example.tbcacademy.domain.model.food

data class LoggedFood(
    val id: String,
    val foodId: String,
    val name: String,
    val amountGrams: Int,
    val calories: Int,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val timestamp: Long
)