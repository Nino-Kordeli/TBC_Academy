package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.domain.repository.CardsRepository
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.common.safeApiCall

class CardsRepositoryImpl(
    private val api: ApiService
) : CardsRepository {

    override suspend fun getCards(): Resource<List<Cards>> {
        return safeApiCall {
            api.getCards()
        }
    }
}
