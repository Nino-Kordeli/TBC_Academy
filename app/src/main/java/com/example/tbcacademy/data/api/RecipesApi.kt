package com.example.tbcacademy.data.api

import com.example.tbcacademy.presentation.model.RecipeUi
import retrofit2.http.GET

interface RecipesApi {
    @GET("recipes")
    suspend fun getRecipes(): List<RecipeUi>
}