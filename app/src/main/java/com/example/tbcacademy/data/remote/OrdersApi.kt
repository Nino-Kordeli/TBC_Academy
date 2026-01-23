package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.dto.OrderDto
import retrofit2.http.GET

interface OrdersApi {
    @GET("orders")
    suspend fun getOrders(): List<OrderDto>
}