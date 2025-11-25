package com.example.tbcacademy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcacademy.common.ApiResult
import com.example.tbcacademy.common.NetworkHelper
import com.example.tbcacademy.data.paging.MessagePagingSource
import com.example.tbcacademy.data.remote.ApiHelper
import com.example.tbcacademy.data.remote.MessageApi
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.model.MessageType
import com.example.tbcacademy.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val api: MessageApi,
    private val networkHelper: NetworkHelper
) : MessageRepository {

    override fun getMessagesPaging(): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MessagePagingSource(api, networkHelper)
            }
        ).flow
    }

    override fun getMessagesPaging(query: String): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MessagePagingSource(api, networkHelper, query)
            }
        ).flow
    }

    override suspend fun getMessagesSafe() = ApiHelper.safeApiCall {
        api.getMessage(page = 1, pageSize = 100).map { dto ->
            val typeString = dto.lastMessageType ?: dto.legacyMessageType ?: "text"
            Message(
                id = dto.id,
                image = dto.image,
                owner = dto.owner,
                lastMessage = dto.lastMessage,
                lastActive = dto.lastActive,
                unreadMessages = dto.unreadMessages,
                isTyping = dto.isTyping,
                lastMessageType = when (typeString.lowercase()) {
                    "text" -> MessageType.TEXT
                    "file" -> MessageType.FILE
                    "voice" -> MessageType.VOICE
                    else -> MessageType.TEXT
                }
            )
        }
    }

    override suspend fun getMessages(): List<Message> {
        return when (val r = getMessagesSafe()) {
            is ApiResult.Success -> r.data
            is ApiResult.Error -> emptyList()
        }
    }
}
