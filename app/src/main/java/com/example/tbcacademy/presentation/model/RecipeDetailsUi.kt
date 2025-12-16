package com.example.tbcacademy.presentation.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeDetailsUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String = "",
    val ingredientIds: List<Int> = emptyList(),
    val directions: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
