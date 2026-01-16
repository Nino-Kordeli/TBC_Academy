package com.example.tbcacademy.presentation.presentation.profile.vm

import com.example.tbcacademy.presentation.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.presentation.common.UserSession
import com.example.tbcacademy.presentation.presentation.profile.contract.ProfileEvent
import com.example.tbcacademy.presentation.presentation.profile.contract.ProfileSideEffect
import com.example.tbcacademy.presentation.presentation.profile.contract.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel
<ProfileUiState, ProfileEvent, ProfileSideEffect>(ProfileUiState()) {

    override fun onEvent(event: ProfileEvent) {
        when(event){
            ProfileEvent.LogoutClicked -> logout()
        }
    }

    private fun logout() {
        UserSession.email = ""
        UserSession.username = ""
        UserSession.isLoggedIn = false

        emitSideEffect(ProfileSideEffect.ProfileToLogin)
    }
}