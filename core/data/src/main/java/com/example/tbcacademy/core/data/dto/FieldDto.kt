package com.example.tbcacademy.core.data.dto

import com.squareup.moshi.Json

data class FieldDto(
    @Json(name = "field_id") val id: Int,
    val hint: String,
    @Json(name = "field_type") val fieldType: String,
    val keyboard: String?,
    val required: Boolean,
    @Json(name = "is_active") val isActive: Boolean,
    val icon: String
)