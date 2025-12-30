package com.example.tbcacademy.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorMessage: String) : Resource<T>()
    data class Loader(val isLoading: Boolean) : Resource<Nothing>()
}
