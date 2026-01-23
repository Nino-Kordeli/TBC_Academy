package com.example.tbcacademy.data.dto

import com.example.tbcacademy.domain.model.MessageType
import com.google.gson.annotations.SerializedName

data class ChatDto(
    val id: Int,
    val image: String?,
    val owner: String,

    @SerializedName("last_message")
    val lastMessage: String,

    @SerializedName("last_active")
    val lastActive: String,

    @SerializedName("unread_messages")
    val unreadMessages: Int,

    @SerializedName("is_typing")
    val isTyping: Boolean,

    @SerializedName("last_message_type")
    val lastMessageType: MessageType
)
