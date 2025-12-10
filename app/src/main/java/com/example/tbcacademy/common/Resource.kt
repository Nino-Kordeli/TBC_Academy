package com.example.tbcacademy.common

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val loading: Boolean = false
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorMessage: String) : Resource<T>(errorMessage = errorMessage)
    class Loading<T>(loading: Boolean = true) : Resource<T>(loading = loading)
}