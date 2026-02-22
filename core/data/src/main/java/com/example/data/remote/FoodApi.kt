package com.example.data.remote

import com.example.data.dto.FoodResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApi {
    @GET("foods")
    suspend fun getFoods(): Response<FoodResponseDto>

    @GET("foods/search")
    suspend fun searchFoods(@Query("query") query: String): Response<FoodResponseDto>

    @GET("foods/{id}")
    suspend fun getFoodById(@Path("id") id: String): Response<FoodResponseDto>

}