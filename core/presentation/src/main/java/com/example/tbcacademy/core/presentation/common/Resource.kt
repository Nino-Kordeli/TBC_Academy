package com.example.tbcacademy.core.presentation.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data class Loader(val isLoading: Boolean) : Resource<Nothing>()
}
