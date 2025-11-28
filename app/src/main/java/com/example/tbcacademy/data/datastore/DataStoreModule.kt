package com.example.tbcacademy.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.credentialsDataStore: DataStore<CredentialsProto> by dataStore(
    fileName = "credentials.pb",
    serializer = CredentialsSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideCredentialsDataStore(@ApplicationContext context: Context): DataStore<CredentialsProto> =
        context.credentialsDataStore
}
