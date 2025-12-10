package com.example.tbcacademy.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class SyncResult(
    val success: Boolean,
    val storiesCount: Int = 0,
    val postsCount: Int = 0,
    val errorMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class SyncEventBus @Inject constructor() {
    private val _syncEvents = MutableStateFlow<SyncResult?>(null)
    val syncEvents = _syncEvents.asStateFlow()

    fun emitSyncResult(result: SyncResult) {
        _syncEvents.value = result
    }
}
