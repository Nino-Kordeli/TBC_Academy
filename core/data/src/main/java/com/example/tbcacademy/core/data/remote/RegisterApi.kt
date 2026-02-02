package com.example.tbcacademy.core.data.remote

import com.example.tbcacademy.core.data.dto.FieldDto
import retrofit2.http.GET

interface RegisterApi {

    @GET("fields")
    suspend fun getFields(): List<List<FieldDto>>
}