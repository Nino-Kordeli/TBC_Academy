package com.example.tbcacademy.data.common

import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SafeFirebaseCall @Inject constructor() {

    fun <T : Any> call(block: suspend () -> T) = flow {
        emit(Resource.Loading(true))
        try {
            val result = block()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
        emit(Resource.Loading(false))
    }
}
