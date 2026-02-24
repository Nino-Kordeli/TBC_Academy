package com.example.data.remote.recipe

import com.example.data.dto.recipe.RecipeResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): Response<RecipeResponseDto>
}