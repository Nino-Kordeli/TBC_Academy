package com.example.tbcacademy.data.remote.service

import com.example.tbcacademy.data.remote.dto.RecipeDto
import retrofit2.http.GET

interface RecipeApi {
    @GET("recipes")
    suspend fun getTrendingRecipes(): List<RecipeDto>
}
