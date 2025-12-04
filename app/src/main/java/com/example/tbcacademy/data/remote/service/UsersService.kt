package com.example.tbcacademy.data.remote.service

import com.example.tbcacademy.data.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersService {
    @GET("v1/3668d139-e182-4fe2-b909-6259524117cb")
    suspend fun fetchUsers(): Response<List<UserResponseDto>>
}