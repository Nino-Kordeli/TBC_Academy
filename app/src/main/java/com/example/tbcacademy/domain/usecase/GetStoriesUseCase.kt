package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repo: StoryRepository
) {
    suspend operator fun invoke() = repo.getStories()
}