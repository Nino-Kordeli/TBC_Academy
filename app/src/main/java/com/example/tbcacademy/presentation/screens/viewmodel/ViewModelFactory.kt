package com.example.tbcacademy.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.data.remote.RetrofitInstance
import com.example.tbcacademy.data.repository.*
import com.example.tbcacademy.domain.usecase.*
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModel
import com.example.tbcacademy.presentation.screens.registration.vm.RegistrationViewModel
import com.example.tbcacademy.presentation.screens.home.vm.HomeViewModel
import com.example.tbcacademy.presentation.screens.profile.vm.ProfileViewModel

object ViewModelFactory {

    private fun provideSessionRepo(ctx: Context) = SessionRepositoryImpl(ctx)

    private fun provideAuthApi(ctx: Context) =
        RetrofitInstance.createService<AuthApi> { provideSessionRepo(ctx).getToken() }

    private fun provideUsersApi(ctx: Context) =
        RetrofitInstance.createService<AuthApi> { provideSessionRepo(ctx).getToken() }

    fun createLoginViewModelFactory(ctx: Context): ViewModelProvider.Factory {
        val api = provideAuthApi(ctx)
        val authRepo = AuthRepositoryImpl(api)
        val sessionRepo = provideSessionRepo(ctx)
        val loginUseCase = LoginUseCase(authRepo, sessionRepo, ctx)

        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                LoginViewModel(loginUseCase) as T
        }
    }

    fun createRegisterViewModelFactory(ctx: Context): ViewModelProvider.Factory {
        val api = provideAuthApi(ctx)
        val authRepo = AuthRepositoryImpl(api)
        val registerUseCase = RegisterUseCase(authRepo)
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                RegistrationViewModel(registerUseCase) as T
        }
    }

    fun createHomeViewModelFactory(ctx: Context): ViewModelProvider.Factory {
        val api = provideUsersApi(ctx)
        val usersRepo = UserRepositoryImpl(api)
        val getUsersUseCase = GetUsersUseCase(usersRepo)
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                HomeViewModel(getUsersUseCase) as T
        }
    }

    fun createProfileViewModelFactory(ctx: Context): ViewModelProvider.Factory {
        val sessionRepo = provideSessionRepo(ctx)
        val getEmail = GetUserEmailUseCase(sessionRepo)
        val logout = LogoutUseCase(sessionRepo)
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ProfileViewModel(getEmail, logout) as T
        }
    }
}