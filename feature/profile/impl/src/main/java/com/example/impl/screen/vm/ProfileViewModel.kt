package com.example.impl.screen.vm

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.LogoutUseCase
import com.example.domain.usecase.user_session.GetNameUseCase
import com.example.domain.usecase.user_session.GetSavedEmailUseCase
import com.example.impl.screen.ProfileEvent
import com.example.impl.screen.ProfileSideEffect
import com.example.impl.screen.ProfileState
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getNameUseCase: GetNameUseCase,
    private val getEmailUseCase: GetSavedEmailUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<ProfileState, ProfileEvent, ProfileSideEffect>(ProfileState()) {

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LogoutCLicked -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()  // clear session
            emitSideEffect(ProfileSideEffect.NavigateToLogin)
        }
    }

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            getNameUseCase.invoke().collect { name ->
                updateState { it.copy(user = name ?: "") }
            }
        }
        viewModelScope.launch {
            val email = getEmailUseCase()
            updateState { it.copy(email = email ?: "") }
        }
    }
}