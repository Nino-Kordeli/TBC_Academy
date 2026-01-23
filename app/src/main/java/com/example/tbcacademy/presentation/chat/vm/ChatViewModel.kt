package com.example.tbcacademy.presentation.chat.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.data.mapper.toUi
import com.example.tbcacademy.data.remote.ChatApi
import com.example.tbcacademy.presentation.chat.contract.ChatEvent
import com.example.tbcacademy.presentation.chat.contract.ChatSideEffect
import com.example.tbcacademy.presentation.chat.contract.ChatState
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.model.ChatUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: ChatApi
) : BaseViewModel<ChatState, ChatEvent, ChatSideEffect>(
    initialState = ChatState()
) {

    private var allChats: List<ChatUiModel> = emptyList()

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            val chats = api.getChats().map { it.toUi() }
            allChats = chats
            updateState { it.copy(chats = chats) }
        }
    }

    override fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnSearchQueryChange ->
                updateState { it.copy(searchQuery = event.value) }

            ChatEvent.OnSearchClick ->
                search()
        }
    }

    private fun search() {
        val query = state.value.searchQuery

        val result =
            if (query.isBlank()) allChats
            else allChats.filter {
                it.owner.contains(query, ignoreCase = true)
            }

        updateState { it.copy(chats = result) }
    }
}
