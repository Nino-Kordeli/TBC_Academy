package com.example.tbcacademy.presentation.screens.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val usersFlow: Flow<PagingData<User>> = repository.getUsersPaging()
        .cachedIn(viewModelScope)
}