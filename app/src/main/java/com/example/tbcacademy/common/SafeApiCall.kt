package com.example.tbcacademy.common

suspend fun <T> safeApiCall(call: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(call())
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown error")
    }
}

