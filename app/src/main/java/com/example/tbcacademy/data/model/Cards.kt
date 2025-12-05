package com.example.tbcacademy.data.model

import com.squareup.moshi.Json

data class Cards(
    val id: Int,
    val location: String,
    @Json(name = "altitude_m") val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int
)
