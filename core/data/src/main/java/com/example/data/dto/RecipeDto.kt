package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseDto(
    val categories: List<RecipeCategoryDto>
)

@Serializable
data class RecipeCategoryDto(
    val id: String,
    val title: String,
    val description: String,
    val recipes: List<RecipeDto>
)

@Serializable
data class RecipeDto(
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
