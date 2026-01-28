package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.presentation.common.Resource

interface CardsRepository {
    suspend fun getCards(): Resource<List<Cards>>
}
