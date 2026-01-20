package com.example.tbcacademy.presentation.products.mapper

import com.example.tbcacademy.domain.model.Category
import com.example.tbcacademy.presentation.model.CategoryUi

fun Category.toUi(): CategoryUi = CategoryUi(
    id = id,
    category = category
)