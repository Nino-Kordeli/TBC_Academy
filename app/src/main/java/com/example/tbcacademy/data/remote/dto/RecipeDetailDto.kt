package com.example.tbcacademy.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeDetailDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val ingredientIds: List<Int>,
    val directions: List<String>,
)