package com.example.tbcacademy.presentation.screens.splash

enum class Effect {
    TO_HOME, TO_LOGIN, TO_PROFILE, BACK
}
data class MessageEffect(val message: String)

object LoadingEffect