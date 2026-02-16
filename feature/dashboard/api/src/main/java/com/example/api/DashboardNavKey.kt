package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object DashboardNavKey : NavKey {
    @Serializable
    object HomeNavKey : NavKey

    @Serializable
    object DiaryNavKey : NavKey

    @Serializable
    object MoreNavKey : NavKey

    @Serializable
    object SearchNavKey : NavKey

    @Serializable
    data class SearchResultsNavKey(val query: String) : NavKey
}