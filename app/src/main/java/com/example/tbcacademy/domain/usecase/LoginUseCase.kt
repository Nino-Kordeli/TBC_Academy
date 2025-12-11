package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(email: String, password: String) = repo.login(email, password)
}