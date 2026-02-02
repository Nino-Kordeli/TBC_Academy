package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.FieldDto
import retrofit2.http.GET

interface RegisterApi {

    @GET("fields")
    suspend fun getFields(): List<List<FieldDto>>
}