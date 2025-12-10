package com.example.tbcacademy.presentation.screen.feed.contract

import androidx.annotation.StringRes
import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.model.Story

data class FeedState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList(),
    val error: String? = null
)

sealed interface FeedEvent{
    object LoadData: FeedEvent
    object RefreshData: FeedEvent
}

sealed interface FeedSideEffect {
    data class ShowError(val message: String? = null, @StringRes val messageResId: Int? = null) : FeedSideEffect
    object ShowLoading : FeedSideEffect
    object HideLoading : FeedSideEffect
}