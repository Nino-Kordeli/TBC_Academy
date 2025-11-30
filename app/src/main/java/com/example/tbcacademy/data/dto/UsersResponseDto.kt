package com.example.tbcacademy.data.dto

import com.example.tbcacademy.domain.model.UsersResponse

data class UsersResponseDto(
    val page: Int,
    val data: List<UserDto>
)

fun UsersResponseDto.toDomain(): UsersResponse {
    return UsersResponse(
        page = page,
        data = data.map { it.toDomain() }
    )
}