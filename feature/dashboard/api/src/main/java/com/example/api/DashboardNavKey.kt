package com.example.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object DashboardNavKey : NavKey {
    @Serializable
    data class HomeNavKey(val calories: Int? = null) : NavKey

    @Serializable
    data object MoreNavKey : NavKey

    @Serializable
    data object SearchNavKey : NavKey

    @Serializable
    data class SearchResultsNavKey(val query: String) : NavKey
}