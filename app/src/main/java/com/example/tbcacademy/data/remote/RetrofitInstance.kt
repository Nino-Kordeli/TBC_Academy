package com.example.tbcacademy.data.remote

import com.example.tbcacademy.data.service.AuthApi
import com.example.tbcacademy.domain.repository.TokenRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://reqres.in/api/"

    fun create(tokenRepository: TokenRepository): AuthApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(tokenRepository = tokenRepository))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("api-key", "key")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthApi::class.java)
    }
}
