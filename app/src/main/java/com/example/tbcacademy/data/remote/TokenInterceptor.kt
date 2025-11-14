package com.example.tbcacademy.data.remote

import com.example.tbcacademy.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenRepository: TokenRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequestBuilder = originalRequest.newBuilder()

        val token = runBlocking { tokenRepository.readToken() }

        if (!token.isNullOrEmpty()) {
            newRequestBuilder.addHeader("Authorization", "Bearer !$token")
        }
        return chain.proceed(newRequestBuilder.build())
    }
}