package com.example.domain.usecase.food

import com.example.domain.repository.food.FoodRepository
import javax.inject.Inject

class SearchFoodsUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    suspend operator fun invoke(query: String) = repository.searchFoods(query)
}