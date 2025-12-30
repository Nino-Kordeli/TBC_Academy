package com.example.tbcacademy.data.dto

import com.squareup.moshi.Json

data class CategoryDto(
    val id: String,
    val name: String,
    @Json(name = "name_de") val nameDe: String?,
    val createdAt: String?,
    val bgl_number: Int?,
    val bgl_variant: String?,
    val order_id: Int?,
    val main: String?,
    val children: List<CategoryDto>?,
)