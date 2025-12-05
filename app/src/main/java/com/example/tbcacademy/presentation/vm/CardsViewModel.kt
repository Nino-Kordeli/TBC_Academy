package com.example.tbcacademy.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.domain.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val repository: CardsRepository
) : BaseViewModel<Unit, Unit, Unit>(Unit) {

    private val _cards = MutableStateFlow<List<Cards>>(emptyList())
    val cards = _cards.asStateFlow()

    fun getCards() {
        viewModelScope.launch {
            _cards.value = repository.getCards()
        }
    }
}
