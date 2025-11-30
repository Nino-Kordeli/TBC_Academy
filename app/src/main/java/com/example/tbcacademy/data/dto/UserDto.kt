package com.example.tbcacademy.data.dto

import com.example.tbcacademy.domain.model.User

data class UserDto(
    val id: Int,
    val email: String,
    val firstname: String,
    val lastname: String,
    val avatar: String
)

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        firstname = firstname,
        lastname = lastname,
        avatar = avatar
    )
}