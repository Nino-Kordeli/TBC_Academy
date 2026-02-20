package com.example.impl.screens.dashboard.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.dashboard.contract.DashboardEvent
import com.example.impl.screens.dashboard.contract.DashboardSideEffect
import com.example.impl.screens.dashboard.contract.DashboardUiState
import com.example.impl.screens.dashboard.model.CaloriesUiModel
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(
    initialState = DashboardUiState()
) {

    init {
        loadCaloriesData()
    }

    override fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.ProfileClicked -> {

            }
            DashboardEvent.GetGoalCalories -> loadCaloriesData()
        }
    }

    private fun loadCaloriesData() {
        viewModelScope.launch {
            userPreferencesRepository.getGoalCalories()
                .collect { goalCalories ->
                    updateState {
                        it.copy(
                            caloriesData = CaloriesUiModel(
                                goal = goalCalories,
                                food = 0,  // TODO: Calculate from logged foods
                                exercise = 0
                            )
                        )
                    }
                }
        }
    }
}