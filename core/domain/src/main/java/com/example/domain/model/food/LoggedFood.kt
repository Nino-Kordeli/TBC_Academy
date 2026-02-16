package com.example.domain.model.food

import kotlinx.serialization.Serializable

@Serializable
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