package com.example.tbcacademy.domain.usecase.firestore

import com.example.tbcacademy.domain.repository.FirestoreRepository
import javax.inject.Inject

class RemoveFavouriteUseCase @Inject constructor(
    private val repository: FirestoreRepository,
) {
    suspend operator fun invoke(recipeId: Int) = repository.removeFavourite(recipeId)
}
