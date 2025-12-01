package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.LoginResponseDto
import com.example.tbcacademy.domain.model.LoginResponse

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(token)
}
