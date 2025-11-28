package com.example.tbcacademy.presentation.registration.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.Credentials
import com.example.tbcacademy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun save(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            authRepository.saveCredentials(firstName, lastName, email)
        }
    }

    fun load(onLoaded: (Credentials) -> Unit) {
        viewModelScope.launch {
            val credentials = authRepository.getCredentials().first()
            onLoaded(credentials)
        }
    }
}