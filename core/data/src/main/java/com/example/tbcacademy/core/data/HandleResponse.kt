package com.example.tbcacademy.core.data

import com.example.tbcacademy.core.domain.common.Resource
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
                emit(Resource.Error(response.errorBody()?.string().orEmpty()))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Resource.Error(e.message.orEmpty()))
                is HttpException -> emit(Resource.Error(e.message.orEmpty()))
                else -> emit(Resource.Error(e.message.orEmpty()))
            }
        }
        emit(Resource.Loader(false))
    }
}
