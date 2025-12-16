package com.example.tbcacademy.presentation.mapper

import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.model.RecipeDetail
import com.example.tbcacademy.presentation.model.RecipeDetailsUi
import com.example.tbcacademy.presentation.model.RecipeUi

fun Recipe.toUi(): RecipeUi = RecipeUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
    isFavourite = true
)

fun RecipeUi.toDomainRecipe(): Recipe = Recipe(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun RecipeDetail.toUi(): RecipeDetailsUi = RecipeDetailsUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description,
    ingredientIds = ingredientIds,
    directions = directions
)

fun List<Recipe>.toUi(): List<RecipeUi> = map { it.toUi() }