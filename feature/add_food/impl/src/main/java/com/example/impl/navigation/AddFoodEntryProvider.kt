package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.AddFoodDetailNavKey
import com.example.api.AddFoodNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.add_food.screen.AddFoodScreen

fun EntryProviderScope<NavKey>.addFoodEntry(
    navigator: Navigator
) {
    entry<AddFoodNavKey> { key ->
        AddFoodScreen(
            mealType = key.mealType,
            onFoodSelected = { food ->
                navigator.navigate(
                    AddFoodDetailNavKey(
                        food = food,
                        mealType = key.mealType
                    )
                )
            }
        )
    }
}