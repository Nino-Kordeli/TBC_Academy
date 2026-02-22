package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.AddFoodDetailNavKey
import com.example.core.navigation.Navigator
import com.example.domain.model.food.Food
import com.example.impl.screens.add_food_details.screen.AddFoodDetailsScreen

fun EntryProviderScope<NavKey>.addFoodDetailsEntry(navigator: Navigator) {
    entry<AddFoodDetailNavKey> { key ->
        AddFoodDetailsScreen(food = key.food)
    }
}