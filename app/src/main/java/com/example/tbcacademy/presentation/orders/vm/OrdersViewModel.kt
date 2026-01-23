package com.example.tbcacademy.presentation.orders.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.OrderStatus
import com.example.tbcacademy.domain.usecase.GetOrdersUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.orders.contract.OrdersEvent
import com.example.tbcacademy.presentation.orders.contract.OrdersSideEffect
import com.example.tbcacademy.presentation.orders.contract.OrdersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : BaseViewModel<OrdersUiState, OrdersEvent, OrdersSideEffect>(
    OrdersUiState()
) {
    init {
        loadOrders()
    }

    private fun changeOrderStatus(orderId: Int, status: OrderStatus) {
        val updatedOrders = state.value.orders.map { order ->
            if (order.id == orderId) {
                order.copy(status = status)
            } else order
        }

        updateState { it.copy(orders = updatedOrders) }
    }

    override fun onEvent(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.StatusSelected -> {
                updateState { it.copy(selectedStatus = event.status) }
            }

            OrdersEvent.Refresh -> {
                loadOrders()
            }

            is OrdersEvent.ChangeOrderStatus ->
                changeOrderStatus(event.orderId, event.newStatus)
        }
    }

    private fun loadOrders() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val orders = getOrdersUseCase()

                val currentOrders = state.value.orders
                val mergedOrders = if (currentOrders.isEmpty()) {
                    orders
                } else {
                    orders.map { newOrder ->
                        val existingOrder = currentOrders.find { it.id == newOrder.id }
                        existingOrder ?: newOrder
                    }
                }

                updateState { it.copy(orders = mergedOrders, isLoading = false) }
            } catch (e: Exception) {
                emitSideEffect(
                    OrdersSideEffect.ShowError(
                        e.message ?: "Failed to load orders"
                    )
                )
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}
