package com.example.tbcacademy.data.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.common.safeApiCall
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.network.ApiService
import com.example.tbcacademy.domain.model.Story
import com.example.tbcacademy.domain.repository.StoryRepository
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val api: ApiService
) : StoryRepository {
    override suspend fun getStories(): List<Story> =
        api.getStories().map { it.toDomain() }

    suspend fun getStoriesSafe(): Resource<List<Story>> = safeApiCall {
        api.getStories().map { it.toDomain() }
    }
}