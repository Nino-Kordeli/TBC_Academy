package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.common.ApiResult
import com.example.tbcacademy.domain.model.Message

interface MessageRepository {
    suspend fun getMessages(): List<Message>
    suspend fun getMessagesSafe(): ApiResult<List<Message>>
}