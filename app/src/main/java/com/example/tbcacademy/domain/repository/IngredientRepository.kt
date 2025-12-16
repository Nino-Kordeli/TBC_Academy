package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.presentation.model.IngredientUi

interface IngredientRepository {
    suspend fun getIngredients(): List<IngredientUi>
}
