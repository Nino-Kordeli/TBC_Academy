package com.example.impl.screens.add_food.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SearchFoodsUseCase
import com.example.impl.screens.add_food.contract.AddFoodEvent
import com.example.impl.screens.add_food.contract.AddFoodState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val searchFoodsUseCase: SearchFoodsUseCase
) : BaseViewModel<AddFoodState, AddFoodEvent, Nothing>(AddFoodState()) {

    init {
        searchFoods("")
    }

    override fun onEvent(event: AddFoodEvent) {
        when (event) {
            is AddFoodEvent.Search -> {
                searchFoods(event.query)
            }
        }
    }

    private fun searchFoods(query: String) {
        viewModelScope.launch {
            updateState { it.copy(query = query) }

            val result = searchFoodsUseCase(query)

            updateState { it.copy(foods = result) }
        }
    }

}
