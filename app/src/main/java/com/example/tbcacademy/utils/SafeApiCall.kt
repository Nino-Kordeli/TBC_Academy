package com.example.tbcacademy.utils

import com.example.tbcacademy.domain.model.Result
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>
): Result<T> = try {
    val response = call()
    if (response.isSuccessful) {
        response.body()?.let { Result.Success(it) }
            ?: Result.Error(Exception("Empty body"))
    } else {
        val errorMsg = response.errorBody()?.string() ?: response.message()
        Result.Error(Exception("HTTP ${response.code()}: $errorMsg"))
    }
} catch (e: IOException) {
    Result.Error(Exception("No internet connection"))
} catch (e: Exception) {
    Result.Error(e)
}