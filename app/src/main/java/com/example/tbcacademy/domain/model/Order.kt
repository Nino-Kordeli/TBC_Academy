package com.example.tbcacademy.domain.model

data class Order(
    val id: Int,
    val orderNumber: String,
    val date: String,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Int,
    val status: OrderStatus
)
