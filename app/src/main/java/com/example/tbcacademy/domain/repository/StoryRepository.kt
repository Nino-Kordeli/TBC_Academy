package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Story

interface StoryRepository {
    suspend fun getStories(): List<Story>
}