package com.example.tbcacademy.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HandleFirebaseResponse {

    fun <T : Any> authCall(call: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            val result = call()
            emit(Resource.Success(data = result))
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        }
        emit(Resource.Loading(loading = false))
    }
}