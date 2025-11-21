package com.example.tbcacademy.data.repository

import com.example.tbcacademy.common.ApiResult
import com.example.tbcacademy.data.remote.ApiHelper
import com.example.tbcacademy.data.remote.MessageApi
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.model.MessageType
import com.example.tbcacademy.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val api: MessageApi
) : MessageRepository {

    override suspend fun getMessagesSafe(): ApiResult<List<Message>> {
        return ApiHelper.safeApiCall {
            api.getMessage().map { dto ->
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
    }

    override suspend fun getMessages(): List<Message> {
        return when(val result = getMessagesSafe()) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> emptyList()
        }
    }
}