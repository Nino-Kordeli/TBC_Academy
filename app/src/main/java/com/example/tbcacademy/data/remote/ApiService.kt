package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.model.Cards
import retrofit2.http.GET

interface ApiService {
    @GET("e3215354-6784-4bae-9bb9-25b39360971b")
    suspend fun getCards(): List<Cards>
}