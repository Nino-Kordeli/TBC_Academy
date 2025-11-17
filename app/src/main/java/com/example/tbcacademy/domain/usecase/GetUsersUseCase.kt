package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val userRepository: com.example.tbcacademy.domain.repository.UserRepository) {
    suspend operator fun invoke(page: Int = 1): Flow<Result<List<User>>> {
        return userRepository.getUsers(page)
    }
}