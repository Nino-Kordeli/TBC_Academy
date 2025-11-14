package com.example.tbcacademy.presentation.screens.register.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.data.repository.TokenRepositoryImpl

class RegisterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            val tokenRepository = TokenRepositoryImpl(context)
            val authRepository = AuthRepositoryImpl(tokenRepository)
            return RegisterViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
