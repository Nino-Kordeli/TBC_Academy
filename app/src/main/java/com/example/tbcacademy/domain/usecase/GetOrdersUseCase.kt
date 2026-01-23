package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrdersRepository
) {
    suspend operator fun invoke(): List<Order> {
        return repository.getOrders()
    }
}