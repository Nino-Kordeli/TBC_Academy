package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.PostResponse
import com.example.tbcacademy.domain.model.Post

fun PostResponse.toDomain() = Post(
    id = id,
    avatar = avatar,
    postDate = postDate,
    firstName = firstName,
    lastName = lastName,
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    postDesc = postDesc,
    canComment = canComment,
    canPostPhoto = canPostPhoto
)