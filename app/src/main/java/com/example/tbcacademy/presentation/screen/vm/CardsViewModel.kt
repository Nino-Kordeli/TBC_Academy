package com.example.tbcacademy.presentation.screen.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.GetCardsUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.screen.contract.CardsEvent
import com.example.tbcacademy.presentation.screen.contract.CardsSideEffect
import com.example.tbcacademy.presentation.screen.contract.CardsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : BaseViewModel<CardsState, CardsEvent, CardsSideEffect>(
    initialState = CardsState(isLoading = true)
) {

    init {
        onEvent(CardsEvent.LoadCards)
    }

    override fun onEvent(event: CardsEvent) {
        when (event) {
            CardsEvent.LoadCards,
            CardsEvent.Retry -> fetchCards()
        }
    }

    private fun fetchCards() {
        updateState { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            when (val result = getCardsUseCase()) {

                is Resource.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        cards = result.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    updateState { it.copy(isLoading = false, error = result.message) }
                    emitSideEffect(CardsSideEffect.ShowToast(result.message ?: "Unknown error"))
                }

                else -> Unit
            }
        }
    }
}
