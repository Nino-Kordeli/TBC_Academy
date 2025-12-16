package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.remote.dto.FirestoreRecipeDto
import com.example.tbcacademy.data.remote.dto.RecipeDetailDto
import com.example.tbcacademy.data.remote.dto.RecipeDto
import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.model.RecipeDetail

fun List<RecipeDto>.recipeToDomain(): List<Recipe> = this.map {
    Recipe(
        id = it.id,
        name = it.name,
        imageUrl = it.imageUrl
    )
}

fun Recipe.toFirestoreDto(): FirestoreRecipeDto = FirestoreRecipeDto(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun FirestoreRecipeDto.firestoreToDomain(): Recipe = Recipe(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun List<RecipeDetailDto>.detailToDomain(): List<RecipeDetail> = this.map {
    RecipeDetail(
        id = it.id,
        name = it.name,
        imageUrl = it.imageUrl,
        description = it.description,
        ingredientIds = it.ingredientIds,
        directions = it.directions
    )
}