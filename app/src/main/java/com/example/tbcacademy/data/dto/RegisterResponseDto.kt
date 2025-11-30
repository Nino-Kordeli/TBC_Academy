    package com.example.tbcacademy.data.dto

    import com.example.tbcacademy.domain.model.RegisterResponse

    data class RegisterResponseDto(
        val id: Int,
        val token: String
    )

    fun RegisterResponseDto.toDomain(): RegisterResponse {
        return RegisterResponse(id, token)
    }