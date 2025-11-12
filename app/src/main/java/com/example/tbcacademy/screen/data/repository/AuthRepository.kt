package com.example.tbcacademy.screen.data.repository

import com.example.tbcacademy.screen.data.remote.RetrofitInstance
import com.example.tbcacademy.screen.data.remote.dto.AuthRequest
import com.example.tbcacademy.screen.data.remote.dto.LoginResponse
import com.example.tbcacademy.screen.data.remote.dto.RegisterResponse
import retrofit2.Response

class AuthRepository {

    suspend fun register(email: String, password: String): Response<RegisterResponse> {
        return RetrofitInstance.api.register(AuthRequest(email, password))
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return RetrofitInstance.api.login(AuthRequest(email, password))
    }
}
