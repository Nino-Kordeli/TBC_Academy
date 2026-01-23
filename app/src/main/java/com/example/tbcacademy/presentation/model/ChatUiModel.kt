package com.example.tbcacademy.presentation.model

import com.example.tbcacademy.domain.model.MessageType

data class ChatUiModel(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType
)
