package com.example.tbcacademy.data.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.common.safeApiCall
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.network.ApiService
import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PostRepository {
    override suspend fun getPosts(): List<Post> =
        api.getPosts().map { it.toDomain() }

    suspend fun getPostsSafe(): Resource<List<Post>> = safeApiCall {
        api.getPosts().map { it.toDomain() }
    }
}