package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repo: PostRepository
) {
    suspend operator fun invoke() = repo.getPosts()
}