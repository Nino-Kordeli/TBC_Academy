package com.example.tbcacademy.data.service

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.dto.LoginResponseDto
import com.example.tbcacademy.data.dto.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("x-api-key: reqres-free-v1","Content-Type: application/json")
    @POST("/api/register")
    suspend fun register(@Body body: AuthRequestDto): Response<RegisterResponseDto>

    @Headers("x-api-key: reqres-free-v1","Content-Type: application/json")
    @POST("/api/login")
    suspend fun login(@Body body: AuthRequestDto): Response<LoginResponseDto>
}