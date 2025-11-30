package com.example.tbcacademy.domain.model

data class UsersResponse(
    val page: Int,
    val data: List<User>
)