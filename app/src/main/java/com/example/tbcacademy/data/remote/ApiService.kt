package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.model.Cards
import retrofit2.http.GET

interface ApiService {
    @GET("locations")
    suspend fun getCards(): List<Cards>
}
