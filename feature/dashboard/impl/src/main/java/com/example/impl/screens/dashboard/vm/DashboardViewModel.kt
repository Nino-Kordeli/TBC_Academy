package com.example.impl.screens.dashboard.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.StepCounterRepository
import com.example.domain.repository.UserPreferencesRepository
import com.example.impl.screens.dashboard.contract.DashboardEvent
import com.example.impl.screens.dashboard.contract.DashboardSideEffect
import com.example.impl.screens.dashboard.contract.DashboardUiState
import com.example.impl.screens.dashboard.model.CaloriesUiModel
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val stepCounterRepository: StepCounterRepository
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(
    initialState = DashboardUiState()
) {
    init {
        loadCaloriesData()
        collectSteps()
    }

    override fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.ProfileClicked -> {}
            DashboardEvent.GetGoalCalories -> loadCaloriesData()
            DashboardEvent.StartStepCounting -> {}
        }
    }

    private fun collectSteps() {
        viewModelScope.launch {
            stepCounterRepository.steps.collect { steps ->
                updateState { it.copy(steps = steps) }
            }
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
                                food = 0,
                                exercise = 0
                            )
                        )
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}