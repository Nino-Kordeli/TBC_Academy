package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Category
import com.example.tbcacademy.domain.model.Product

interface ProductsRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getProducts(): List<Product>
}
