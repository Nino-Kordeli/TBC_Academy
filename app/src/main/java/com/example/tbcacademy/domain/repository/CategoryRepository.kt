package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.dto.CategoryDto

interface CategoryRepository {
    suspend fun getCategories(): Resource<List<CategoryDto>>
}