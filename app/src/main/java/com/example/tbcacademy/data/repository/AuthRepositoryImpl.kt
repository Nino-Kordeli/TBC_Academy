package com.example.tbcacademy.data.repository

import androidx.datastore.core.DataStore
import com.example.tbcacademy.data.datastore.CredentialsProto
import com.example.tbcacademy.domain.model.Credentials
import com.example.tbcacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<CredentialsProto>
) : AuthRepository {

    override suspend fun saveCredentials(
        firstName: String,
        lastName: String,
        email: String
    ) {
        dataStore.updateData { current ->
            current.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build()
        }
    }

    override fun getCredentials(): Flow<Credentials> {
        return dataStore.data.map { proto ->
            Credentials(proto.firstName, proto.lastName, proto.email)
        }
    }
}