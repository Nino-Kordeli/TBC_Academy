package com.example.tbcacademy.presentation.screens.contacts.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.model.ContactsState
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _state = MutableStateFlow<ContactsState>(ContactsState.LOADING)
    val state: StateFlow<ContactsState> = _state.asStateFlow()

    private var fullMessageList = listOf<Message>()

    init {
        fetchMessages()
    }

    private fun fetchMessages() {
        viewModelScope.launch {
            _state.value = ContactsState.LOADING
            when(val result = repository.getMessagesSafe()) {
                is com.example.tbcacademy.common.ApiResult.Success -> {
                    fullMessageList = result.data
                    _messages.value = fullMessageList
                    _state.value = ContactsState.SUCCESS
                }
                is com.example.tbcacademy.common.ApiResult.Error -> {
                    _state.value = ContactsState.ERROR
                }
            }
        }
    }

    fun onSearch(query: String) {
        _messages.value = if (query.isBlank()) {
            fullMessageList
        } else {
            fullMessageList.filter { it.owner.contains(query, ignoreCase = true) }
        }
    }
}