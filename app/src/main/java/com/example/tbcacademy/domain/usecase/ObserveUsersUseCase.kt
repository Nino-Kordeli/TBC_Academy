package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.UserRepository
import javax.inject.Inject

class ObserveUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke() = userRepository.observeUsers()
}