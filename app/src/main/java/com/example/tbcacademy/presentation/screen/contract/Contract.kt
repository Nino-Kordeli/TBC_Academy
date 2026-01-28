package com.example.tbcacademy.presentation.screen.contract

import com.example.tbcacademy.data.model.Cards

data class CardsState(
    val isLoading: Boolean = false,
    val cards: List<Cards> = emptyList(),
    val error: String? = null
)

sealed interface CardsEvent {
    data object LoadCards : CardsEvent
    data object Retry : CardsEvent
}

sealed interface CardsSideEffect {
    data class ShowToast(val message: String) : CardsSideEffect
}
