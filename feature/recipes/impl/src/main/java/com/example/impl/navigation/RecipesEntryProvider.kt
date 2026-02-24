package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.RecipesNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.screen.RecipeScreen

fun EntryProviderScope<NavKey>.recipesEntry(navigator: Navigator) {
    entry<RecipesNavKey.RecipesNavKey> {
        RecipeScreen()
    }
}