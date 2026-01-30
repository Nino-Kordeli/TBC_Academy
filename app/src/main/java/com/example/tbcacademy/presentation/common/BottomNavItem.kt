package com.example.tbcacademy.presentation.common

import com.example.tbcacademy.R

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
) {
    object Cards : BottomNavItem("cards", R.drawable.ic_heart, "Cards")
    object Feed : BottomNavItem("feed", R.drawable.ic_home, "Feed")
}