package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signOut()
}