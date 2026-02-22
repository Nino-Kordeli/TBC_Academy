package com.example.api

import androidx.navigation3.runtime.NavKey
import com.example.domain.model.food.Food
import kotlinx.serialization.Serializable

@Serializable
data class AddFoodDetailNavKey(val food: Food) : NavKey