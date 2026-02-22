package com.example.impl.screens.add_food_details.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.model.food.Food
import com.example.domain.usecase.food.GetAllFoodUseCase
import com.example.domain.usecase.food.LogFoodUseCase
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsEvent
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsSideEffect
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodDetailsViewModel @Inject constructor(
    private val logFoodUseCase: LogFoodUseCase
) : BaseViewModel<AddFoodDetailsState, AddFoodDetailsEvent, AddFoodDetailsSideEffect>
    (AddFoodDetailsState()) {

    override fun onEvent(event: AddFoodDetailsEvent) {
        when (event) {
            is AddFoodDetailsEvent.LoadFood -> loadFood(event.food)
            is AddFoodDetailsEvent.ServingsChanged -> {
                updateState { it.copy(numberOfServings = event.servings) }
            }

            AddFoodDetailsEvent.SaveFood -> saveFood()
        }
    }

    private fun loadFood(food: Food) {
        updateState { it.copy(food = food) }
    }

    private fun saveFood() {
        val currentFood = state.value.food ?: return
        val servings = state.value.numberOfServings
        val mealType = state.value.mealType

        viewModelScope.launch {
            val gramsTotal = servings * 100
            logFoodUseCase(currentFood, gramsTotal, mealType)

            emitSideEffect(AddFoodDetailsSideEffect.NavigateBack)
        }
    }
}
