package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.ProfileNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screen.profile.ProfileScreen

fun EntryProviderScope<NavKey>.profileEntry(navigator: Navigator) {
    entry<ProfileNavKey.ProfileNavKey> {
        ProfileScreen(navigator)
    }
}