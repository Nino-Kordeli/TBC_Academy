package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.StoryResponse
import com.example.tbcacademy.domain.model.Story

fun StoryResponse.toDomain() = Story(
    id = id,
    title = title,
    cover = cover
)