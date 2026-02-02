package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.FieldDto
import com.example.tbcacademy.domain.model.Field

fun FieldDto.toDomain() = Field(
    id = id,
    hint = hint,
    type = fieldType,
    keyboard = keyboard,
    required = required,
    isActive = isActive,
    icon = icon
)
