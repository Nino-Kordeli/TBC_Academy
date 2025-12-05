package com.example.tbcacademy.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.domain.usecase.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _cardsState = MutableStateFlow<Resource<List<Cards>>>(Resource.Loading())
    val cardsState: StateFlow<Resource<List<Cards>>> = _cardsState

    fun getCards() {
        viewModelScope.launch {
            _cardsState.value = Resource.Loading()
            _cardsState.value = getCardsUseCase()
        }
    }
}
