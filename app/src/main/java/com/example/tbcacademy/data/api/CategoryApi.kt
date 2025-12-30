package com.example.tbcacademy.data.api

import com.example.tbcacademy.data.dto.CategoryDto
import retrofit2.http.GET

interface CategoryApi {
    @GET("v1/0c08be03-49c2-493b-951c-6ba8a397dc72")
    suspend fun getCategories(): List<CategoryDto>
}
