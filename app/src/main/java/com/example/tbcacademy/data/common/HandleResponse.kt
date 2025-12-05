package com.example.tbcacademy.data.common

import com.example.tbcacademy.common.Resource

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown error")
    }
}
