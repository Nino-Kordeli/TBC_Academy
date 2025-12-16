package com.example.tbcacademy.domain.usecase.firestore

import com.example.tbcacademy.domain.model.Recipe
import com.example.tbcacademy.domain.repository.FirestoreRepository
import javax.inject.Inject

class SaveFavouriteUseCase @Inject constructor(
    private val repository: FirestoreRepository,
) {
    suspend operator fun invoke(recipe: Recipe) = repository.saveFavourite(recipe)
}