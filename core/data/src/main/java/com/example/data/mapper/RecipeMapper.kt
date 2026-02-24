package com.example.data.mapper

import com.example.data.dto.RecipeCategoryDto
import com.example.data.dto.RecipeDto
import com.example.domain.model.recipe.Recipe
import com.example.domain.model.recipe.RecipeCategory

fun RecipeCategoryDto.toDomain() = RecipeCategory(
    id = id,
    title = title,
    description = description,
    recipes = recipes.map { it.toDomain() }
)

fun RecipeDto.toDomain() = Recipe(
    id = id,
    title = title,
    description = description,
    prepTime = prepTime,
    cookTime = cookTime,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fat = fat,
    imageUrl = imageUrl
)
