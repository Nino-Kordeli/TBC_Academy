package com.example.tbcacademy.data.api

import com.example.tbcacademy.data.remote.dto.RecipeDetailDto
import com.example.tbcacademy.data.remote.dto.RecipeDto
import retrofit2.Response
import retrofit2.http.GET


interface RecipesApi {
    @GET("/recipes")
    suspend fun getRecipes(): Response<List<RecipeDto>>

    @GET("/recipesDetails")
    suspend fun getRecipeDetails(): Response<List<RecipeDetailDto>>
}