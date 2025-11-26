package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.dto.LoginResponseDto
import com.example.tbcacademy.data.dto.RegisterResponseDto
import com.example.tbcacademy.data.dto.UsersResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @Headers("x-api-key: reqres-free-v1", "Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body request: AuthRequestDto): Response<LoginResponseDto>

    @Headers("x-api-key: reqres-free-v1", "Content-Type: application/json")
    @POST("register")
    suspend fun register(@Body request: AuthRequestDto): Response<RegisterResponseDto>

    @Headers("x-api-key: reqres-free-v1", "Content-Type: application/json")
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UsersResponseDto>
}