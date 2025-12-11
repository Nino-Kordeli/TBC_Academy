package com.example.tbcacademy.data.common

import kotlinx.coroutines.flow.flow

class SafeCall {
    fun <T : Any> call(block: suspend () -> T) = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(block()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}