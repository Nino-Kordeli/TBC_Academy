package com.example.data.repository

import com.example.data.local.JsonFoodDataSource
import com.example.domain.repository.food.FoodRepository
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonFoodDataSource
) : FoodRepository {

    override suspend fun searchFoods(query: String) = jsonDataSource.searchFoods(query)

    override suspend fun getFoodById(id: String) = jsonDataSource.getFoodById(id)

    override fun getAllFoods() = jsonDataSource.getAllFoods()
}