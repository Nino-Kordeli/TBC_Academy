package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Story
import com.example.tbcacademy.presentation.common.Resource

interface StoryRepository {
    suspend fun getStories(): Resource<List<Story>>
}