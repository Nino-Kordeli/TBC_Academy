package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.domain.repository.CardsRepository
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CardsRepository {
    override suspend fun getCards(): List<Cards> = api.getCards()
}
