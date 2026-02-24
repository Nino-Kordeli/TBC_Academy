package com.example.domain.model.recipe

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val prepTime: String,
    val cookTime: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val imageUrl: String
)