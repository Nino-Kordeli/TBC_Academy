package com.example.tbcacademy.core.data.remote

import com.example.tbcacademy.core.data.dto.FieldDto
import retrofit2.Response
import retrofit2.http.GET

interface RegisterApi {

    @GET("fields")
    suspend fun getRegisterFields(): Response<List<FieldDto>>
}