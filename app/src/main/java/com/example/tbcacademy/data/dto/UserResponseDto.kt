package com.example.tbcacademy.data.dto

import com.squareup.moshi.Json

data class UserResponseDto(
    val id: Int,
    @Json(name = "full_name") val fullName: String?,
    val email: String?,
    @Json(name = "activation_status") val activationStatus: Int?,
    @Json(name = "last_active_description") val lastActiveDescription: String?,
    @Json(name = "last_active_epoch") val lastActiveEpoch: Long?,
    @Json(name = "profile_image_url") val profileImageUrl: String?
)