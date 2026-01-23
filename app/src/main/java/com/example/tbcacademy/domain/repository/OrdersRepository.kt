package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Order

interface OrdersRepository {
    suspend fun getOrders(): List<Order>
}