package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.data.dto.LoginResponseDto
import com.example.tbcacademy.data.dto.RegisterResponseDto
import com.example.tbcacademy.data.dto.UsersResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: AuthRequestDto): Response<LoginResponseDto>

    @POST("register")
    suspend fun register(@Body request: AuthRequestDto): Response<RegisterResponseDto>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UsersResponseDto>
}
