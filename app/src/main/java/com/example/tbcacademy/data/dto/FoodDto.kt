package com.example.tbcacademy.data.dto

data class FoodDto(
    val id: String,
    val name: String,
    val calories: Int,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val isMeal: Boolean
)
