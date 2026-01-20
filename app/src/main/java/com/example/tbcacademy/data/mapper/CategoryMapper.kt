package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.CategoryDto
import com.example.tbcacademy.domain.model.Category

fun CategoryDto.toDomain(): Category =
    Category(
        id = id,
        category = category
    )
