package com.example.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.api.DiaryNavKey
import com.example.core.navigation.Navigator
import com.example.impl.screens.diary.screen.DiaryScreen

fun EntryProviderScope<NavKey>.diaryEntry(navigator: Navigator) {
    entry<DiaryNavKey.DiaryNavKey> {
        DiaryScreen(navigator = navigator)
    }
}