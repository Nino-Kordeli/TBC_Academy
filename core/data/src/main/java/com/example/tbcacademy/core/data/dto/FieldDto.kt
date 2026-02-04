package com.example.tbcacademy.core.data.dto

import com.google.gson.annotations.SerializedName

data class FieldDto(
    @SerializedName("field_id")
    val id: Int,
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("field_type")
    val type: String?,
    @SerializedName("keyboard")
    val keyboard: String?,
    @SerializedName("required")
    val required: Boolean?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("icon")
    val icon: String?
)