package com.example.domain.usecase

import com.example.domain.model.food.Food
import com.example.domain.repository.FoodRepository
import javax.inject.Inject

class SearchFoodsUseCase @Inject constructor(
    private val repository: FoodRepository
) {

    suspend operator fun invoke(query: String): List<Food> {
        return repository.searchFoods(query)
    }
}
