package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.remote.dto.RecipeDto
import com.example.tbcacademy.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe =
    Recipe(
        id = id,
        imageUrl = imageUrl,
        name = name
    )

fun List<RecipeDto>.toDomain(): List<Recipe> =
    map { it.toDomain() }
