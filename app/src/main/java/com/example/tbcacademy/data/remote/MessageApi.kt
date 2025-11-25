package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.MessageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageApi {
    @GET("d7d9436b-21c5-43f7-82f9-2334163351cf")
    suspend fun getMessage(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<MessageDto>
}