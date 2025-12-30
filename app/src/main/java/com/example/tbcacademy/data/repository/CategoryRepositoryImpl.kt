package com.example.tbcacademy.data.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.common.safeApiCall
import com.example.tbcacademy.data.api.CategoryApi
import com.example.tbcacademy.data.dto.CategoryDto
import com.example.tbcacademy.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val api: CategoryApi,
) : CategoryRepository {

    override suspend fun getCategories(): Resource<List<CategoryDto>> {
        var result: Resource<List<CategoryDto>> = Resource.Loader(true)

        safeApiCall(
            call = { api.getCategories() },
            onStart = {
                result = Resource.Loader(true)
            },
            onSuccess = {
                result = Resource.Success(it)
            },
            onError = {
                result = Resource.Error(it)
            }
        )

        return result
    }
}