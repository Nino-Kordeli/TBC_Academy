package com.example.data.repository

import com.example.data.local.JsonFoodDataSource
import com.example.domain.model.food.Food
import com.example.domain.repository.FoodRepository
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonFoodDataSource
) : FoodRepository {

    override suspend fun searchFoods(query: String): List<Food> {
        return jsonDataSource.searchFoods(query)
    }

    override suspend fun getFoodById(id: String): Food? {
        return jsonDataSource.getFoodById(id)
    }
}