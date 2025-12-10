package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.repository.PostRepositoryImpl
import com.example.tbcacademy.domain.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepositoryImpl
) {
    suspend operator fun invoke(): Resource<List<Post>> = repository.getPostsSafe()
}
