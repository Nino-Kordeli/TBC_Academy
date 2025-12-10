package com.example.tbcacademy.data.network

import com.example.tbcacademy.data.model.PostResponse
import com.example.tbcacademy.data.model.StoryResponse
import retrofit2.http.GET

interface ApiService {

    @GET("0f76d541-3832-4a3c-927a-0593e060d6da")
    suspend fun getStories(): List<StoryResponse>

    @GET("1e3f40b1-19a5-4986-ad60-fdc80c27234b")
    suspend fun getPosts(): List<PostResponse>
}