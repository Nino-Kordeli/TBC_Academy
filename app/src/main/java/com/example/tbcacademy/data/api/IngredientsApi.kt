package com.example.tbcacademy.data.api

import com.example.tbcacademy.data.remote.dto.IngredientDto
import com.example.tbcacademy.domain.model.Ingredient
import com.example.tbcacademy.presentation.model.IngredientUi
import retrofit2.Response
import retrofit2.http.GET

interface IngredientsApi {
    @GET("/ingredients")
    suspend fun getIngredients(): Response<List<IngredientDto>>
}
