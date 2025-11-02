package com.example.tbcacademy.screen.my_orders.model

data class Product(
    val title: String,
    val color: String,
    val quantity: Int,
    val price: String,
    val buttonTitle: String,
    val status: Status
)
