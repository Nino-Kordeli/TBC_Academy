package com.example.tbcacademy.data.api

import com.example.tbcacademy.presentation.model.IngredientUi
import retrofit2.http.GET

interface IngredientsApi {
    @GET("/ingredients")
    suspend fun getIngredients(): List<IngredientUi>
}
