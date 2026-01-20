package com.example.tbcacademy.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val price: String,
    val image: String? = null,
    val category: String
)