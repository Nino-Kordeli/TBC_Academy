package com.example.tbcacademy.presentation.screens.add_food.vm

import androidx.lifecycle.viewModelScope
//import com.example.tbcacademy.domain.usecase.SearchFoodsUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.screens.add_food.contract.AddFoodEvent
import com.example.tbcacademy.presentation.screens.add_food.contract.AddFoodState
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
class AddFoodViewModel @Inject constructor(
    private val searchFoodsUseCase: SearchFoodsUseCase
) : BaseViewModel<AddFoodState, AddFoodEvent, Nothing>(AddFoodState()) {

    override fun onEvent(event: AddFoodEvent) {
        when (event) {
            is AddFoodEvent.Search -> {
                viewModelScope.launch {
                    val result = searchFoodsUseCase(event.query)

                    */
/*updateState {
                        copy(
                            query = event.query,
                            foods = result,
                            input = TODO(),
                            output = TODO()
                        )
                    }*//*

                }
            }
        }
    }
}*/
