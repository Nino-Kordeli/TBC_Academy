package com.example.tbcacademy.presentation.screens.dashboard.vm

import com.example.tbcacademy.domain.usecase.DashboardUseCase
import com.example.ui.base.BaseViewModel
import com.example.tbcacademy.presentation.screens.dashboard.contract.DashboardEvent
import com.example.tbcacademy.presentation.screens.dashboard.contract.DashboardSideEffect
import com.example.tbcacademy.presentation.screens.dashboard.contract.DashboardUiState
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val DashboardUsecase: DashboardUseCase
) : BaseViewModel<DashboardUiState, DashboardEvent, DashboardSideEffect>(
    DashboardUiState(
        default = TODO()
    )
) {
}