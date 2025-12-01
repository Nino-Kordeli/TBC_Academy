package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.AuthRequestDto
import com.example.tbcacademy.domain.model.AuthRequest

fun AuthRequestDto.toDomain(): AuthRequest {
    return AuthRequest(email, password)
}
