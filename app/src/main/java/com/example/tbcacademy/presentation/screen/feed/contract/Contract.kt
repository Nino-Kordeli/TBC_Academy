package com.example.tbcacademy.presentation.screen.feed.contract

import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.model.Story

data class FeedState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList()
)

sealed interface FeedEvent {
    data object LoadData : FeedEvent
    data object RefreshData : FeedEvent
}

sealed interface FeedSideEffect {
    data class ShowError(val message: String) : FeedSideEffect
}