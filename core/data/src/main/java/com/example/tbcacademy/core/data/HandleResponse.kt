package com.example.tbcacademy.core.data

import com.example.tbcacademy.core.domain.common.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HandleResponse @Inject constructor() {

    fun <T> safeApiCall(apiCall: suspend () -> Response<T>) = flow {
        emit(Resource.Loader(true))

        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("Empty response"))
                }
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                emit(Resource.Error(error))
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is IOException -> "Network error: ${e.message}"
                is HttpException -> "HTTP error: ${e.message}"
                else -> "Error: ${e.message}"
            }
            emit(Resource.Error(errorMessage))
        }
    }.catch { e ->
        emit(Resource.Error("Flow error: ${e.message}"))
    }
}