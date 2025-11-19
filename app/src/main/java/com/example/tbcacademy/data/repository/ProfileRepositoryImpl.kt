package com.example.tbcacademy.data.repository

import com.example.tbcacademy.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {
    override suspend fun getProfileEmail() = "reqres@example.com"
}