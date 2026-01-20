package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Category
import com.example.tbcacademy.domain.repository.ProductsRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): List<Category> =
        repository.getCategories()
}
