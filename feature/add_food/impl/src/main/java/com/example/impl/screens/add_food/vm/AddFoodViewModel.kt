package com.example.impl.screens.add_food.vm

import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.usecase.food.GetAllFoodUseCase
import com.example.impl.screens.add_food.contract.AddFoodEvent
import com.example.impl.screens.add_food.contract.AddFoodState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodUseCase
) : BaseViewModel<AddFoodState, AddFoodEvent, Nothing>(AddFoodState()) {

    init {
        fetchFoods()
    }

    override fun onEvent(event: AddFoodEvent) {
        when (event) {
            is AddFoodEvent.Search -> searchFoods(event.query)
        }
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                foodList = resource.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun searchFoods(query: String) {
        updateState { state ->
            state.copy(
                query = query,
                foodList = if (query.isBlank()) state.foodList
                else state.foodList.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
        }
    }
}
