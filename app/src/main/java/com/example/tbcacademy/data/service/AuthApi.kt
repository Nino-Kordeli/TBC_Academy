package com.example.tbcacademy.data.service

import com.example.tbcacademy.data.dto.AuthRequest
import com.example.tbcacademy.data.dto.LoginResponse
import com.example.tbcacademy.data.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("x-api-key: reqres-free-v1","Content-Type: application/json")
    @POST("/api/register")
    suspend fun register(@Body body: AuthRequest): Response<RegisterResponse>

    @Headers("x-api-key: reqres-free-v1","Content-Type: application/json")
    @POST("/api/login")
    suspend fun login(@Body body: AuthRequest): Response<LoginResponse>
}