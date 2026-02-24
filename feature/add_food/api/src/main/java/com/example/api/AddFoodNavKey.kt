package com.example.api

import androidx.navigation3.runtime.NavKey
import com.example.model.MealType
import kotlinx.serialization.Serializable

@Serializable
data class AddFoodNavKey(
    val mealType: MealType
) : NavKey {
}