package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val api: AuthApi) : UserRepository {
    override suspend fun getUsers(page: Int): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            val resp = api.getUsers(page)
            if (resp.isSuccessful) {
                val users = resp.body()?.data?.map {
                    User(it.id, it.email, it.avatar)
                } ?: emptyList()
                emit(Result.Success(users))
            } else emit(Result.Error(Exception("${resp.code()} ${resp.message()}")))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}