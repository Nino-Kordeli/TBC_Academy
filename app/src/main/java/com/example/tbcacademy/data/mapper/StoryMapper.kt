package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.model.StoryResponse
import com.example.tbcacademy.domain.model.Story

fun StoryResponse.toDomain(): Story = Story(
    title = title,
    cover = cover
)