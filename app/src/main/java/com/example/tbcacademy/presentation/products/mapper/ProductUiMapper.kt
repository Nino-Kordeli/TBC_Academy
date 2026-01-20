package com.example.tbcacademy.presentation.products.mapper

import com.example.tbcacademy.domain.model.Product
import com.example.tbcacademy.presentation.model.ProductUi

fun Product.toUi(): ProductUi = ProductUi(
    id = id,
    title = title,
    price = price,
    image = image,
    category = category
)