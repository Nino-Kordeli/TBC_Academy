package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object QuizNavKey : NavKey {
    @Serializable
    object QuizKey : NavKey
}