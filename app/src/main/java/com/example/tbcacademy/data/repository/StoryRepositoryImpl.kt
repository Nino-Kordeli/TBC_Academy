package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.domain.model.Story
import com.example.tbcacademy.domain.repository.StoryRepository
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.common.safeApiCall
import javax.inject.Inject
import com.example.tbcacademy.data.mapper.toDomain
import kotlin.collections.map

class StoryRepositoryImpl @Inject constructor(
    private val api: ApiService
) : StoryRepository {
    override suspend fun getStories(): Resource<List<Story>> =
        safeApiCall { api.getStories().map { it.toDomain() } }
}