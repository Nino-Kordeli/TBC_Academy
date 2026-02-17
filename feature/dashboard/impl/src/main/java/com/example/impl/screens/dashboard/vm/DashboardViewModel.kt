package com.example.impl.screens.dashboard.vm

import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.usecase.dashboard.DashboardUseCase
import com.example.impl.screens.dashboard.model.CaloriesUiModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.impl.screens.dashboard.contract.DashboardEvent
import com.example.impl.screens.dashboard.contract.DashboardSideEffect
import com.example.impl.screens.dashboard.contract.DashboardUiState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val DashboardUsecase: DashboardUseCase
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(DashboardUiState(true)) {

    val caloriesUiModel =
        userPreferencesRepository.getGoalCalories()
            .map { goal ->
                CaloriesUiModel(
                    goal = goal,
                    food = 0,
                    exercise = 0
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CaloriesUiModel(2000, 0, 0)
            )
}

