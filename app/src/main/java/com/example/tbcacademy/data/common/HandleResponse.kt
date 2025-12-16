package com.example.tbcacademy.data.common

import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> apiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Error(errorMessage = response.errorBody()?.string().orEmpty()))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        }
        emit(Resource.Loading(loading = false))
    }
}