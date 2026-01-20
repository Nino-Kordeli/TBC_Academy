package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.remote.ProductsApi
import com.example.tbcacademy.domain.model.Category
import com.example.tbcacademy.domain.model.Product
import com.example.tbcacademy.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi
): ProductsRepository {

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map { it.toDomain() }
    }

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().map { it.toDomain() }
    }
}