package com.example.domain.usecase.user_session

import com.example.domain.repository.user_session.UserSessionRepository
import javax.inject.Inject

class GetSavedEmailUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    suspend operator fun invoke() = userSessionRepository.getSavedEmail()
}