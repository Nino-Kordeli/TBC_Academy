package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.dto.ProductDto
import com.example.tbcacademy.domain.model.Product

fun ProductDto.toDomain(): Product =
    Product(
        id = id,
        title = title,
        price = price,
        image = image,
        category = category
    )
