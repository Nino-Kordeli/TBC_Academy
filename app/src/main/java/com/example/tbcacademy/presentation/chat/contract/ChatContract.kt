package com.example.tbcacademy.presentation.chat.contract

import com.example.tbcacademy.presentation.model.ChatUiModel

sealed interface ChatEvent {
    data class OnSearchQueryChange(val value: String) : ChatEvent
    object OnSearchClick : ChatEvent
}

data class ChatState(
    val chats: List<ChatUiModel> = emptyList(),
    val searchQuery: String = ""
)

sealed interface ChatSideEffect
