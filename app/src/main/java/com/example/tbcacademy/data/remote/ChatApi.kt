package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.ChatDto
import retrofit2.http.GET

interface ChatApi {

    @GET("chats")
    suspend fun getChats(): List<ChatDto>
}
