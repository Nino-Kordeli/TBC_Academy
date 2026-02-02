package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.remote.RegisterApi
import com.example.tbcacademy.domain.model.Field
import com.example.tbcacademy.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    private val api: RegisterApi
) : RegisterRepository {

    override suspend fun getRegisterFields(): List<Field> {
        return api.getFields()
            .flatten()
            .filter { it.isActive }
            .map { it.toDomain() }
    }
}