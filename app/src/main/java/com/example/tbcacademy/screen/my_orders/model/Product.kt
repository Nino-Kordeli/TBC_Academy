package com.example.tbcacademy.screen.my_orders.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val color: ItemColor,
    val quantity: Int,
    val price: String,
    val status: Status,
    @DrawableRes val imageRes: Int
) : Serializable