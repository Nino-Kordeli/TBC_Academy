package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.OrderDto
import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.model.OrderStatus

fun OrderDto.toDomain(): Order {
    return Order(
        id = id,
        orderNumber = order_number,
        date = date,
        trackingNumber = tracking_number,
        quantity = quantity,
        subtotal = subtotal,
        status = OrderStatus.valueOf(status)
    )
}