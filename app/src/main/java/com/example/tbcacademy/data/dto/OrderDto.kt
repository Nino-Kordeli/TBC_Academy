package com.example.tbcacademy.data.dto

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("order_number")
    val order_number: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("tracking_number")
    val tracking_number: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("subtotal")
    val subtotal: Int,
    @SerializedName("status")
    val status: String
)