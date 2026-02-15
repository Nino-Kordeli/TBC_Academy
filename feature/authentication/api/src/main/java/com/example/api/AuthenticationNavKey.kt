package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object AuthenticationNavKey : NavKey {
    @Serializable
    object WelcomeNavKey : NavKey

    @Serializable
    object LoginNavKey : NavKey

    @Serializable
    object RegisterNavKey : NavKey

}