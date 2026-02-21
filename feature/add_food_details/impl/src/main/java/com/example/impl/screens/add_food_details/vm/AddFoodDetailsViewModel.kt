package com.example.impl.screens.add_food_details.vm

import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.domain.usecase.food.GetAllFoodUseCase
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsEvent
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsSideEffect
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsState
import com.example.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFoodDetailsViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodUseCase
) : BaseViewModel<
        AddFoodDetailsState,
        AddFoodDetailsEvent,
        AddFoodDetailsSideEffect>
    (AddFoodDetailsState()) {

    override fun onEvent(event: AddFoodDetailsEvent) {
        when (event) {
            AddFoodDetailsEvent.FetchFoods -> fetchFoods()
        }

    }

    private fun fetchFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {}
                    is Resource.Success<*> -> {

                    }
                }
            }
        }
    }
}
