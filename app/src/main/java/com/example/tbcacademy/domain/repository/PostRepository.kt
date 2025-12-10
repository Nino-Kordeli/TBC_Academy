package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}