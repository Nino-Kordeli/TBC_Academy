package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.remote.dto.IngredientDto
import com.example.tbcacademy.domain.model.Ingredient

fun List<IngredientDto>.toDomain(): List<Ingredient> = this.map {
    Ingredient(
        id = it.id,
        name = it.name,
        imageUrl = it.imageUrl
    )
}