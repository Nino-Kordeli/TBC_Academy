package com.example.tbcacademy.presentation.screens.profile.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcacademy.data.repository.TokenRepositoryImpl

class ProfileViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val tokenRepo = TokenRepositoryImpl(context)
        return ProfileViewModel(tokenRepo) as T
    }
}