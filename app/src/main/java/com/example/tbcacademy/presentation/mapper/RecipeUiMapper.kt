package com.example.tbcacademy.presentation.mapper

import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.presentation.model.RecipeUi

fun Recipe.toUi(): RecipeUi =
    RecipeUi(
        id = id,
        title = name,
        imageUrl = imageUrl
    )

fun List<Recipe>.toUi(): List<RecipeUi> =
    map { it.toUi() }
