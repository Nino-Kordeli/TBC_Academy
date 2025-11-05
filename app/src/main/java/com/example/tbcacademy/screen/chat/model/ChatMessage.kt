package com.example.tbcacademy.screen.chat.model

data class ChatMessage(
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isLeft: Boolean = true
)
