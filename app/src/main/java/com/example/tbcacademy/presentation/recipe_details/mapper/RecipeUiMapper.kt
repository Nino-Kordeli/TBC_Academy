package com.example.tbcacademy.presentation.recipe_details.mapper

import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.model.RecipeDetail
import com.example.tbcacademy.presentation.model.RecipeUi

fun Recipe.toUi(): RecipeUi = RecipeUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = "",
    ingredientIds = emptyList(),
    directions = emptyList()
)

fun RecipeDetail.toUi(): RecipeUi = RecipeUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description,
    ingredientIds = ingredientIds,
    directions = directions
)

fun List<Recipe>.toUi(): List<RecipeUi> = map { it.toUi() }