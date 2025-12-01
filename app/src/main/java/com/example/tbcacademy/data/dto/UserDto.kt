package com.example.tbcacademy.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int,
    val email: String,
    @SerializedName("first_name")
    val firstname: String,
    @SerializedName("last_name")
    val lastname: String,
    val avatar: String
)