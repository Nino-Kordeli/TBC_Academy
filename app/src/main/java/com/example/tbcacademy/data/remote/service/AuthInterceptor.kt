package com.example.tbcacademy.data.remote.service

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }
}