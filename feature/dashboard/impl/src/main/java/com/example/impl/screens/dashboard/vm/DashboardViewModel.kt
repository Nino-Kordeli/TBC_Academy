package com.example.impl.screens.dashboard.vm

import com.example.domain.usecase.DashboardUseCase
import com.example.impl.screens.dashboard.contract.DashboardEvent
import com.example.impl.screens.dashboard.contract.DashboardSideEffect
import com.example.impl.screens.dashboard.contract.DashboardUiState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val DashboardUsecase: DashboardUseCase
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(
    DashboardUiState(
        default = true
    )
) {
}