package com.example.ui.components.bottom_bar

sealed interface BottomBarDestination {
    data object Home : BottomBarDestination
    data object Diary : BottomBarDestination
    data object More : BottomBarDestination
    data object Search : BottomBarDestination
}
