package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.repository.StoryRepositoryImpl
import com.example.tbcacademy.domain.model.Story
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepositoryImpl
) {
    suspend operator fun invoke(): Resource<List<Story>> = repository.getStoriesSafe()
}
