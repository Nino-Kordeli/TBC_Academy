package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.CategoryDto
import com.example.tbcacademy.data.dto.ProductDto
import retrofit2.http.GET

interface ProductsApi {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("events")
    suspend fun getProducts(): List<ProductDto>
}