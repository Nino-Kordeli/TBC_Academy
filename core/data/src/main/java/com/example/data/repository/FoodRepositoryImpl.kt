package com.example.data.repository

import com.example.common.mapper.asResource
import com.example.common.resource.Resource
import com.example.data.mapper.food.toDomain
import com.example.data.remote.FoodApi
import com.example.data.response_handler.retrofit.HandleRetrofitResponse
import com.example.domain.model.food.Food
import com.example.domain.repository.food.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodApi: FoodApi,
    private val handleResponse: HandleRetrofitResponse
) : FoodRepository {

    override suspend fun getAllFoods(): Flow<Resource<List<Food>>> {
        return handleResponse.apiCall { foodApi.getFoods() }.asResource { it.toDomain() }
    }
}