package com.example.domain.usecase.user_session

import com.example.domain.repository.user_session.UserSessionRepository
import javax.inject.Inject

class SaveEmailUseCase @Inject constructor(
    private val sessionRepository: UserSessionRepository

) {
    suspend operator fun invoke(email: String) {
        sessionRepository.saveEmail(email)
    }
}