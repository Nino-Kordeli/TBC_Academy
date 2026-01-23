package com.example.tbcacademy.domain.model

import com.google.gson.annotations.SerializedName

enum class MessageType {
    @SerializedName("text")
    TEXT,

    @SerializedName("voice")
    VOICE,

    @SerializedName("file")
    FILE
}