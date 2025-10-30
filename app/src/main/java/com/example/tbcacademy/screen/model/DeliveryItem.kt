package com.example.tbcacademy.screen.model

data class DeliveryItem(
    val orderId: String,
    val trackingNumber: String,
    val status: Status,
    val price: String,
    val date: Long,
    val quantity: String
)