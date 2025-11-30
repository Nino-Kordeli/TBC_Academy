package com.example.tbcacademy.data.dto

import com.example.tbcacademy.domain.model.AuthRequest

data class AuthRequestDto(
    val email: String,
    val password: String
)

fun AuthRequestDto.toDomain(): AuthRequest {
    return AuthRequest(email, password)
}