package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.PostResponse
import com.example.tbcacademy.data.dto.StoryResponse
import retrofit2.http.GET

interface ApiService {

    @GET("story")
    suspend fun getStories():List<StoryResponse>

    @GET("post")
    suspend fun getPosts():List<PostResponse>
}
