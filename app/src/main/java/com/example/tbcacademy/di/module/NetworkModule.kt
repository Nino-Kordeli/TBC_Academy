package com.example.tbcacademy.di.module

import android.content.Context
import com.example.tbcacademy.common.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}
