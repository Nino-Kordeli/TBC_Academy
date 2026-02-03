package com.example.tbcacademy.core.presentation.common

import com.example.tbcacademy.core.domain.common.Resource

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Error")
    }
}