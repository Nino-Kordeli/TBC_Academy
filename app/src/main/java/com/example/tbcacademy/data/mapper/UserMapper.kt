package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.UserDto
import com.example.tbcacademy.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        firstname = firstname,
        lastname = lastname,
        avatar = avatar
    )
}