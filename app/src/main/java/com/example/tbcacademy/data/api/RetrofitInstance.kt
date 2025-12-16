package com.example.tbcacademy.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: IngredientsApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://212.58.103.2")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(IngredientsApi::class.java)
    }
}
