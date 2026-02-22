package com.example.domain.usecase.user_session

import com.example.domain.repository.UserSessionRepository
import javax.inject.Inject

class GetRememberMeUseCase @Inject constructor(
    private val sessionRepository: UserSessionRepository
) {
    suspend operator fun invoke() = sessionRepository.getRememberMe()
}