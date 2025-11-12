package com.example.tbcacademy.screen.data.remote

import com.example.tbcacademy.screen.data.remote.dto.AuthRequest
import com.example.tbcacademy.screen.data.remote.dto.LoginResponse
import com.example.tbcacademy.screen.data.remote.dto.RegisterResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("x-api-key: reqres-free-v1")
    @POST("api/register")
    suspend fun register(@Body body: AuthRequest): Response<RegisterResponse>

    @Headers("x-api-key: reqres-free-v1")
    @POST("api/login")
    suspend fun login(@Body body: AuthRequest): Response<LoginResponse>
}