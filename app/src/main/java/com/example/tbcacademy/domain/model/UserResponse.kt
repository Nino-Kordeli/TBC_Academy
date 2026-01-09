package com.example.tbcacademy.domain.model

data class UserResponse(
    val page: Int,
    val data: List<User>
)