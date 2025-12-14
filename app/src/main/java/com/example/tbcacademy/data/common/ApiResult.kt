package com.example.tbcacademy.data.common

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val throwable: Throwable? = null) : ApiResult<Nothing>()
}