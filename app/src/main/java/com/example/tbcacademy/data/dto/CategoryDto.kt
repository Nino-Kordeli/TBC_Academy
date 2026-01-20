package com.example.tbcacademy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val category: String
)
