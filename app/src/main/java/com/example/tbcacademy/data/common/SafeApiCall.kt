package com.example.tbcacademy.data.common

suspend fun <T> safeApiCall(
    call: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: Exception) {
        ApiResult.Error(e)
    }
}
