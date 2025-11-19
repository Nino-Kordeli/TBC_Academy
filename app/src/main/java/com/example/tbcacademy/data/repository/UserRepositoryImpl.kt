package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import com.example.tbcacademy.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @Named("noAuth") private val api: AuthApi
) : UserRepository {

    override suspend fun getUsers(page: Int): Result<List<User>> {
        val result = safeApiCall { api.getUsers(page) }
        return when (result) {
            is Result.Success -> {
                val users = result.data.data.map { User(it.id, it.email, it.avatar) }
                Result.Success(users)
            }

            is Result.Error -> result
            else -> Result.Error(Exception("Unknown error"))
        }
    }

    override suspend fun getUser(): User {
        return when (val result = getUsers(1)) {
            is Result.Success -> result.data.first()
            is Result.Error -> throw result.exception
            else -> throw Exception("Unknown error")
        }
    }
}
