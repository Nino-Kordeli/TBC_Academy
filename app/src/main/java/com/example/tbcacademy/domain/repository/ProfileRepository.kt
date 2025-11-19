package com.example.tbcacademy.domain.repository

interface ProfileRepository {
    suspend fun getProfileEmail(): String
}