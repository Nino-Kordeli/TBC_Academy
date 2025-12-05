package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.domain.repository.CardsRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend operator fun invoke(): Resource<List<Cards>> {
        return repository.getCards()
    }
}