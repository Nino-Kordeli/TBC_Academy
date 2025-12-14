package com.example.tbcacademy.presentation.home.contract

import com.example.tbcacademy.domain.model.Location

data class HomeState(
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class HomeEvent {
    object LoadLocations : HomeEvent()
}

sealed interface HomeSideEffect {
    data class Error(val messageRes: Int) : HomeSideEffect
}