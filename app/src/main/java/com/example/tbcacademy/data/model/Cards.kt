package com.example.tbcacademy.data.model

import com.google.gson.annotations.SerializedName

data class Cards(
    val id: Int,
    val location: String,
    @SerializedName("altitude_m")
    val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int
)