package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.dto.CategoryDto
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.domain.model.Category
import com.example.tbcacademy.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(query: String): Resource<List<Category>> {
        return when (val result = repository.getCategories()) {
            is Resource.Success -> {
                val flattened = mutableListOf<Category>()
                result.data.forEach { traverse(it, 0, flattened) }
                val filtered = if (query.isBlank()) flattened
                else flattened.filter { it.name.contains(query, true) }
                Resource.Success(filtered)
            }

            is Resource.Error -> Resource.Error(result.errorMessage)
            is Resource.Loader -> Resource.Loader(result.isLoading)
        }
    }

    private fun traverse(
        dto: CategoryDto,
        depth: Int,
        result: MutableList<Category>,
    ) {
        result.add(dto.toDomain(depth))

        dto.children?.forEach {
            traverse(it, depth + 1, result)
        }
    }
}
