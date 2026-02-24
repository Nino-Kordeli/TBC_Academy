package com.example.data.remote

import com.example.data.dto.food.FoodResponseDto
import com.example.data.dto.recipe.RecipeResponseDto
import com.example.data.dto.workout.WorkoutResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface FoodApi {
    @GET("foods")
    suspend fun getFoods(): Response<FoodResponseDto>

    @GET("workouts")
    suspend fun getWorkouts(): Response<WorkoutResponseDto>

    @GET("recipes")
    suspend fun getRecipes(): Response<RecipeResponseDto>
}