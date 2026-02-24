package com.example.domain.model.recipe

data class RecipeCategory(
    val id: String,
    val title: String,
    val description: String,
    val recipes: List<Recipe>
)