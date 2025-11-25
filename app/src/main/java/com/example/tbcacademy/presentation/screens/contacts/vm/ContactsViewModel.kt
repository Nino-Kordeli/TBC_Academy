package com.example.tbcacademy.presentation.screens.contacts.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcacademy.common.NetworkHelper
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: MessageRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val _isConnected = MutableStateFlow(networkHelper.isNetworkConnected())
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagedMessages: StateFlow<PagingData<Message>> = queryFlow
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (_isConnected.value) {
                repository.getMessagesPaging(query)
            } else {
                flowOf(PagingData.empty())
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        observeNetwork()
    }

    fun onSearch(query: String) {
        queryFlow.value = query.trim()
    }

    private fun observeNetwork() {
        networkHelper.registerNetworkCallback(
            onAvailable = {
                viewModelScope.launch {
                    _isConnected.value = true
                    queryFlow.value = queryFlow.value
                }
            },
            onLost = {
                viewModelScope.launch {
                    _isConnected.value = false
                }
            }
        )
    }
}
