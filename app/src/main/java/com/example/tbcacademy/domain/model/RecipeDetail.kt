package com.example.tbcacademy.domain.model

data class RecipeDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val ingredientIds: List<Int>,
    val directions: List<String>
)