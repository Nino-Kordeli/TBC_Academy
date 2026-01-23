package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.ChatDto
import com.example.tbcacademy.presentation.model.ChatUiModel

fun ChatDto.toUi(): ChatUiModel =
    ChatUiModel(
        id = id,
        image = image,
        owner = owner,
        lastMessage = lastMessage,
        lastActive = lastActive,
        unreadMessages = unreadMessages,
        isTyping = isTyping,
        lastMessageType = lastMessageType
    )
