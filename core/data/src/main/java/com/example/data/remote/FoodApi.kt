package com.example.data.remote

import com.example.data.dto.FoodResponseDto
import com.example.data.dto.RecipeResponseDto
import com.example.data.dto.WorkoutResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApi {
    @GET("foods")
    suspend fun getFoods(): Response<FoodResponseDto>

    @GET("workouts")
    suspend fun getWorkouts(): Response<WorkoutResponseDto>

    @GET("recipes")
    suspend fun getRecipes(): Response<RecipeResponseDto>

}