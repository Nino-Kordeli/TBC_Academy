package com.example.tbcacademy.presentation.home.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.data.common.ApiResult
import com.example.tbcacademy.domain.repository.LocationRepository
import com.example.tbcacademy.presentation.home.contract.HomeEvent
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LocationRepository,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadLocations -> loadLocations()
        }
    }

    private fun loadLocations() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = repository.getLocations()) {
                is ApiResult.Success -> {
                    updateState {
                        it.copy(
                            locations = result.data,
                            isLoading = false
                        )
                    }
                }

                is ApiResult.Error -> {
                    updateState { it.copy(isLoading = false) }
                    emitSideEffect(
                        HomeSideEffect.Error(R.string.failed_to_load_locations)
                    )
                }
            }
        }
    }

}

