package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.api.IngredientsApi
import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.data.mapper.base.asResource
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.domain.model.Ingredient
import com.example.tbcacademy.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val api: IngredientsApi,
    private val handleResponse: HandleResponse,
) : IngredientRepository {

    override suspend fun getIngredients(): Flow<Resource<List<Ingredient>>> {
        return handleResponse.apiCall {
            api.getIngredients()
        }.asResource { it.toDomain() }
    }
}