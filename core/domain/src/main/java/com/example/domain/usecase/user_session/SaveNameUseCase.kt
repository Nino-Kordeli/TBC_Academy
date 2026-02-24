package com.example.domain.usecase.user_session

import com.example.domain.repository.user_session.UserSessionRepository
import javax.inject.Inject

class SaveNameUseCase @Inject constructor(
    private val sessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(name: String) {
        sessionRepository.saveName(name)
    }
}