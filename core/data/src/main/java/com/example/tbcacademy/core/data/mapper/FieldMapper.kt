package com.example.tbcacademy.core.data.mapper

import com.example.tbcacademy.core.data.dto.FieldDto
import com.example.tbcacademy.core.domain.model.Field

fun FieldDto.toDomain() = Field(
    id = id,
    hint = hint,
    type = fieldType,
    keyboard = keyboard,
    required = required,
    isActive = isActive,
    icon = icon
)
