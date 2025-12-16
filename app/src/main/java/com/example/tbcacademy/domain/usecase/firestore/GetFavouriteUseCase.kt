package com.example.tbcacademy.domain.usecase.firestore

import com.example.tbcacademy.domain.repository.FirestoreRepository
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: FirestoreRepository,
) {
    operator fun invoke() = repository.getFavourites()
}