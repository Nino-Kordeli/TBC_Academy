package com.example.impl.screens.add_food.vm

import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.usecase.food.GetAllFoodUseCase
import com.example.impl.screens.add_food.contract.AddFoodEvent
import com.example.impl.screens.add_food.contract.AddFoodSideEffect
import com.example.impl.screens.add_food.contract.AddFoodState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodUseCase
) : BaseViewModel<AddFoodState, AddFoodEvent, Nothing>(AddFoodState()) {

    override fun onEvent(event: AddFoodEvent) {
        when (event) {
            is AddFoodEvent.Search -> searchFoods(event.query)
            AddFoodEvent.FetchFoods -> fetchFoods()
        }
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        updateState { it.copy(
                            allFoods = resource.data,
                            foodList = resource.data
                        )}
                    }
                }
            }
        }
    }

    private fun searchFoods(query: String) {
        updateState { state ->
            state.copy(
                query = query,
                foodList = if (query.isBlank()) state.allFoods
                else state.allFoods.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
        }
    }
}

    private fun searchFoods(query: String) {
//        viewModelScope.launch {
//            updateState { it.copy(query = query) }
//
//            val result = searchFoodsUseCase(query)
//
//            updateState { it.copy(foods = result) }
//        }
    }
