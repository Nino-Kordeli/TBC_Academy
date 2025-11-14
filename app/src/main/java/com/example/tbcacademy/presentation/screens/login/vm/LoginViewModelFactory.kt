package com.example.tbcacademy.presentation.screens.login.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.data.repository.TokenRepositoryImpl

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val tokenRepo = TokenRepositoryImpl(context)
        val authRepo = AuthRepositoryImpl(tokenRepo)
        return LoginViewModel(authRepo, tokenRepo) as T
    }
}