package com.example.tbcacademy.data.remote.dto

data class LocationDto(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)
