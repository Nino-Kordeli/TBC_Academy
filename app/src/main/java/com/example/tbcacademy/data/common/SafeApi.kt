package com.example.tbcacademy.data.common

suspend fun <T> safeApiCall(
    call: suspend () -> T,
    onStart: (() -> Unit)? = null,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit
) {
    try {
        onStart?.invoke()
        val result = call()
        onSuccess(result)
    } catch(e: Exception) {
        onError(e.localizedMessage ?: "Unknown error")
    }
}