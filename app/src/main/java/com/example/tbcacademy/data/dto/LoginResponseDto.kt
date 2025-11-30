package com.example.tbcacademy.data.dto

import com.example.tbcacademy.domain.model.LoginResponse

data class LoginResponseDto(
    val token: String
)

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(token)
}