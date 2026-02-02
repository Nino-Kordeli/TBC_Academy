package com.example.tbcacademy.core.data.repository

import com.example.tbcacademy.core.data.mapper.toDomain
import com.example.tbcacademy.core.data.remote.RegisterApi
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.core.domain.repository.RegisterRepository

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