package com.example.tbcacademy.data.repository

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.common.safeApiCall
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.domain.repository.CardsRepository

class CardsRepositoryImpl(private val api: ApiService) : CardsRepository {

    override suspend fun getCards(): Resource<List<Cards>> {
        return safeApiCall { api.getCards() }
    }
}
