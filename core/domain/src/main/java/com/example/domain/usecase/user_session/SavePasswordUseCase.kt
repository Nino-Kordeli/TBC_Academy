package com.example.domain.usecase.user_session

import com.example.domain.repository.UserSessionRepository
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val sessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(password: String) {
        sessionRepository.savePassword(password)
    }
}