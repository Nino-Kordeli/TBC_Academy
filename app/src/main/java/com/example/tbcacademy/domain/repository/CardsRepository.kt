package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.model.Cards

interface CardsRepository {
    suspend fun getCards(): List<Cards>
}