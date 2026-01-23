package com.example.tbcacademy.presentation.orders.contract

import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.model.OrderStatus

data class OrdersUiState(
    val orders: List<Order> = emptyList(),
    val selectedStatus: OrderStatus = OrderStatus.PENDING,
    val isLoading: Boolean = false
)

sealed interface OrdersEvent {
    object Refresh : OrdersEvent
    data class StatusSelected(val status: OrderStatus) : OrdersEvent
    data class ChangeOrderStatus(
        val orderId: Int,
        val newStatus: OrderStatus
    ) : OrdersEvent
}

sealed class OrdersSideEffect {
    data class ShowError(val message: String) : OrdersSideEffect()
}
