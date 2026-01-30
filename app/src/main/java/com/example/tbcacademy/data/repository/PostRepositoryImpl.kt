package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.repository.PostRepository
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.common.safeApiCall
import javax.inject.Inject
import com.example.tbcacademy.data.mapper.toDomain
import kotlin.collections.map

class PostRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PostRepository {
    override suspend fun getPosts(): Resource<List<Post>> =
        safeApiCall { api.getPosts().map { it.toDomain() } }
}