package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Field

interface RegisterRepository {
    suspend fun getRegisterFields(): List<Field>
}
