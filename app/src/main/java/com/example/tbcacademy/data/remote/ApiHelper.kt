package com.example.tbcacademy.data.remote

import retrofit2.HttpException
import com.example.tbcacademy.common.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object ApiHelper {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                ApiResult.Success(response)
            } catch (e: IOException) {
                ApiResult.Error(e, "Network error: ${e.localizedMessage}")
            } catch (e: HttpException) {
                ApiResult.Error(e, "Server error: ${e.code()} ${e.message()}")
            } catch (e: Exception) {
                ApiResult.Error(e, "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}