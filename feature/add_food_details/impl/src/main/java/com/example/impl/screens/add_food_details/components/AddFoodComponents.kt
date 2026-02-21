package com.example.impl.screens.add_food_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun IngredientMass(amount: String, label: String) {
    Column {
        Text(amount)
        Text(label)
    }
}