package com.example.domain.usecase.food

import com.example.domain.repository.food.FoodRepository
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    suspend operator fun invoke() = repository.getAllFoods()
}