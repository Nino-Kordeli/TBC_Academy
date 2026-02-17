package com.example.domain.usecase.food

import com.example.domain.model.food.Food
import com.example.domain.repository.food.FoodRepository
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(): List<Food> {
        return repository.getAllFoods()
    }
}