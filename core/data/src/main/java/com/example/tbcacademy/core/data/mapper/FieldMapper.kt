package com.example.tbcacademy.core.data.mapper

import com.example.tbcacademy.core.data.dto.FieldDto
import com.example.tbcacademy.core.domain.model.Field

fun FieldDto.toDomain() = Field(
    id = id,
    hint = hint.orEmpty(),
    type = type.orEmpty(),
    keyboard = keyboard,
    required = required ?: false,
    isActive = isActive ?: false,
    icon = icon.orEmpty(),
    value = ""
)