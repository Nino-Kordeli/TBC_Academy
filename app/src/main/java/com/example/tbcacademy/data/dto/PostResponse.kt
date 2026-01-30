package com.example.tbcacademy.data.dto

data class PostResponse(
    val id: Int,
    val avatar: String?,
    val postDate: Long,
    val firstName: String,
    val lastName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String?,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)