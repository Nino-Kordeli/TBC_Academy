package com.example.tbcacademy.presentation.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientUi (
    val id: Int,
    val name: String,
    val imageUrl: String
)