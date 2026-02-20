package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object DiaryNavKey: NavKey {
    @Serializable
    data object DiaryNavKey : NavKey
}