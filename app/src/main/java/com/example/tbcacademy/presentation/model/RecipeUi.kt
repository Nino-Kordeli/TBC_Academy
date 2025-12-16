package com.example.tbcacademy.presentation.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavourite: Boolean = false
)
