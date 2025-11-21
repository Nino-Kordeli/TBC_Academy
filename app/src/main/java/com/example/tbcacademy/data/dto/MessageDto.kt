package com.example.tbcacademy.data.dto

import com.squareup.moshi.Json

data class MessageDto(
    val id: Int,
    val image: String?,
    val owner: String,
    @Json(name = "last_message") val lastMessage: String,
    @Json(name = "last_active") val lastActive: String,
    @Json(name = "unread_messages") val unreadMessages: Int,
    @Json(name = "is_typing") val isTyping: Boolean,
    @Json(name = "last_message_type") val lastMessageType: String? = null,
    @Json(name = "laste_message_type") val legacyMessageType: String? = null
)