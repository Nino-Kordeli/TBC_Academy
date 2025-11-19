package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileEmailUseCase @Inject constructor(private val repo: ProfileRepository) {
    suspend operator fun invoke() = repo.getProfileEmail()
}
