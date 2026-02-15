package com.example.tbcacademy.presentation.screens.search.vm

//import com.example.tbcacademy.domain.usecase.SearchFoodsUseCase
import com.example.ui.base.BaseViewModel
import com.example.tbcacademy.presentation.screens.search.contract.SearchEvent
import com.example.tbcacademy.presentation.screens.search.contract.SearchSideEffect
import com.example.tbcacademy.presentation.screens.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
//    private val searchFoodsUseCase: SearchFoodsUseCase
) : BaseViewModel<SearchState, SearchEvent, SearchSideEffect
        >(SearchState()) {

    override fun onEvent(event: SearchEvent) {
        when (event) {

            is SearchEvent.OnQueryChanged -> {
                updateState { it.copy(query = event.query) }
            }

            SearchEvent.SearchClicked -> {
//                searchFoods()
            }
        }
    }

//    private fun searchFoods() {
//        val query = state.value.query
//
//        if (query.isBlank()) return
//
//        viewModelScope.launch {
//            updateState { it.copy(isLoading = true) }
//
//            try {
//                val foods = searchFoodsUseCase(query)
//
//                updateState {
//                    it.copy(
//                        foods = foods.map { food ->
//                            FoodModel(
//                                id = food.id,
//                                name = food.name,
//                                calories = food.calories
//                            )
//                        },
//                        isLoading = false
//                    )
//                }
//
//            } catch (e: Exception) {
//                updateState { it.copy(isLoading = false) }
//                emitSideEffect(
//                    SearchSideEffect.ShowError(
//                        e.message ?: "Unknown error"
//                    )
//                )
//            }
//        }
//    }
}