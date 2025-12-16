package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.api.IngredientsApi
import com.example.tbcacademy.domain.repository.IngredientRepository
import com.example.tbcacademy.presentation.model.IngredientUi
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val api: IngredientsApi
) : IngredientRepository {

    override suspend fun getIngredients(): List<IngredientUi> {
        return api.getIngredients().map { dto ->
            IngredientUi(
                id = dto.id,
                name = dto.name,
                imageUrl = dto.imageUrl
            )
        }
    }
}
