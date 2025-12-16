package com.example.tbcacademy.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
)
