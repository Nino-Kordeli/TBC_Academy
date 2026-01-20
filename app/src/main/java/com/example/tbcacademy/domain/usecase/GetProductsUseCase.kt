package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Product
import com.example.tbcacademy.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): List<Product> =
        repository.getProducts()
}
