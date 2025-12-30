package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.CategoryDto
import com.example.tbcacademy.domain.model.Category

fun CategoryDto.toDomain(depth: Int): Category {
    return Category(
        id = id,
        name = name,
        depth = depth.coerceAtMost(4)
    )
}
