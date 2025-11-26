package com.example.tbcacademy.presentation.screens.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcacademy.data.dto.UserDto
import com.example.tbcacademy.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserDto>> = Pager(
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = { userRepository.getUsersPaging() }
    ).flow.cachedIn(viewModelScope)
}