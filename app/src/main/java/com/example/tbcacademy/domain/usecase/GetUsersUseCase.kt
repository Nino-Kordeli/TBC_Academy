package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<User>> =
        repository.getUsers(page)
}