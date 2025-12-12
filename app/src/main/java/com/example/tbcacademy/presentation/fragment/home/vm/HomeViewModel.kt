package com.example.tbcacademy.presentation.fragment.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.datastore.UserPreferences
import com.example.tbcacademy.domain.usecase.SignOutUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val userEmail: String = "",
    val isLoggedOut: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val auth: FirebaseAuth,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _state.value = _state.value.copy(
                userEmail = currentUser.email ?: "User"
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            signOutUseCase()

            userPreferences.clearLogin()

            _state.value = _state.value.copy(isLoggedOut = true)
        }
    }
}