package com.example.data.repository

import com.example.common.mapper.asResource
import com.example.common.retrofit.HandleRetrofitResponse
import com.example.data.mapper.toDomain
import com.example.data.remote.FoodApi
import com.example.domain.model.food.Food
import com.example.domain.repository.food.FoodRepository
import kotlinx.coroutines.flow.Flow
import com.example.common.resource.Resource
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
//    private val jsonDataSource: JsonFoodDataSource
    private val foodApi: FoodApi,
    private val handleResponse: HandleRetrofitResponse
) : FoodRepository {

    //    override suspend fun searchFoods(query: String) = jsonDataSource.searchFoods(query)
//
//    override suspend fun getFoodById(id: String) = jsonDataSource.getFoodById(id)
//
//    override fun getAllFoods() = jsonDataSource.getAllFoods()
    override suspend fun getAllFoods(): Flow<Resource<List<Food>>> {
        return handleResponse.apiCall { foodApi.getFoods() }.asResource {
            it.toDomain()
        }
    }

    override suspend fun searchFoods(query: String): Flow<Resource<List<Food>>> {
        return handleResponse.apiCall { foodApi.searchFoods(query) }.asResource {
            it.toDomain()
        }
    }
}