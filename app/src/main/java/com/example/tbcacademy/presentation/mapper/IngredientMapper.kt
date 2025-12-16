package com.example.tbcacademy.presentation.mapper

import com.example.tbcacademy.domain.model.Ingredient
import com.example.tbcacademy.presentation.model.IngredientUi

fun Ingredient.toUi(): IngredientUi = IngredientUi(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun List<Ingredient>.toUi(): List<IngredientUi> = map { it.toUi() }