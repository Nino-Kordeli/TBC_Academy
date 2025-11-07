package com.example.tbcacademy.screen.register.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FieldItem(
    @Json(name = "field_id") val fieldId: Int,
    val hint: String,
    @Json(name = "field_type") val fieldType: String,
    val keyboard: String? = null,
    val required: Boolean = false,
    @Json(name = "is_active") val isActive: Boolean = true,
    val icon: String,
    var userValue: String = ""
)