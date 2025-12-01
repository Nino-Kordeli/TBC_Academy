package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.RegisterResponseDto
import com.example.tbcacademy.domain.model.RegisterResponse

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(id, token)
}
