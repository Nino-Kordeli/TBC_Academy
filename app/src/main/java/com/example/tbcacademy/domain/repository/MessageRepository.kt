package com.example.tbcacademy.domain.repository

import androidx.paging.PagingData
import com.example.tbcacademy.common.ApiResult
import com.example.tbcacademy.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(): List<Message>
    suspend fun getMessagesSafe(): ApiResult<List<Message>>
    fun getMessagesPaging(): Flow<PagingData<Message>>
    fun getMessagesPaging(query: String = ""): Flow<PagingData<Message>>
}