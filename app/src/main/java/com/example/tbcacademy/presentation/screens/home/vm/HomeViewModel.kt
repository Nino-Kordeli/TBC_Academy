package com.example.tbcacademy.presentation.screens.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tbcacademy.domain.repository.UserRepository
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.screens.home.contract.HomeEvent
import com.example.tbcacademy.presentation.screens.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.screens.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {

    val usersFlow = repository.getUsersPaging()
        .cachedIn(viewModelScope)
}