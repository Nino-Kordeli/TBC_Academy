package com.example.tbcacademy.presentation.screens.profile.contract

sealed class ProfileEvent {
    object LogoutSuccess : ProfileEvent()
}