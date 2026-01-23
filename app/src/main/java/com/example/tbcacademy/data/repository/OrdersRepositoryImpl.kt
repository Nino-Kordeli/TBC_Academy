package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.remote.OrdersApi
import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {

    override suspend fun getOrders(): List<Order> {
        return api.getOrders().map { it.toDomain() }
    }
}