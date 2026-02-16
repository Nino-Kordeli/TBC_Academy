package com.example.data.local

import android.content.Context
import com.example.data.dto.FoodResponse
import com.example.data.mapper.toDomain
import com.example.domain.model.food.Food
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonFoodDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    private var cachedFoods: List<Food>? = null

    private fun loadFoods(): List<Food> {
        if (cachedFoods != null) return cachedFoods!!

        val jsonString = context.assets.open("foods.json").bufferedReader().use { it.readText() }
        val response = Json.decodeFromString<FoodResponse>(jsonString)
        cachedFoods = response.foods.map { it.toDomain() }
        return cachedFoods!!
    }

    fun searchFoods(query: String): List<Food> {
        val foods = loadFoods()
        if (query.isBlank()) return foods

        return foods.filter { food ->
            food.name.contains(query, ignoreCase = true)
        }
    }

    fun getFoodById(id: String): Food? {
        return loadFoods().find { it.id == id }
    }

    fun getAllFoods(): List<Food> = loadFoods()
}