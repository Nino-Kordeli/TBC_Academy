package com.example.tbcacademy.presentation.screens.splash.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.domain.usecase.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase
) : ViewModel() {

    private val _destination = MutableLiveData<SplashDestination>()
    val destination: LiveData<SplashDestination> = _destination

    init {
        viewModelScope.launch {
            delay(700)
            val isLoggedIn = checkSessionUseCase()

            if (isLoggedIn) {
                _destination.value = SplashDestination.Home
            } else {
                _destination.value = SplashDestination.Login
            }
        }
    }
}

sealed class SplashDestination {
    object Home : SplashDestination()
    object Login : SplashDestination()
}