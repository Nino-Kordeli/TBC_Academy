package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FoodResponseDto(
    val foods: List<FoodDto>
)

@Serializable
data class FoodDto(
    val id: String,
    val name: String,
    val calories: Int,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val isMeal: Boolean
)