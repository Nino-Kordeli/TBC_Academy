package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.presentation.common.Resource

interface PostRepository {
    suspend fun getPosts(): Resource<List<Post>>
}