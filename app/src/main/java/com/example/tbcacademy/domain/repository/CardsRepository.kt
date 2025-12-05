package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.model.Cards

interface CardsRepository {
    suspend fun getCards(): Resource<List<Cards>>
}