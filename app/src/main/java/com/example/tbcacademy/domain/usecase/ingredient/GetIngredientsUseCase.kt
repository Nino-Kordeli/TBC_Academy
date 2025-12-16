package com.example.tbcacademy.domain.usecase.ingredient

import com.example.tbcacademy.domain.repository.IngredientRepository
import javax.inject.Inject

class GetIngredientsUseCase @Inject constructor(
    private val repository: IngredientRepository,
) {
    suspend operator fun invoke() = repository.getIngredients()
}