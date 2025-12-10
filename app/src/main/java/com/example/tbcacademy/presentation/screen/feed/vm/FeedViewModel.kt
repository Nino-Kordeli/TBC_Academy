package com.example.tbcacademy.presentation.screen.feed.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.common.BaseViewModel
import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.domain.usecase.GetPostsUseCase
import com.example.tbcacademy.domain.usecase.GetStoriesUseCase
import com.example.tbcacademy.presentation.screen.feed.contract.FeedEvent
import com.example.tbcacademy.presentation.screen.feed.contract.FeedSideEffect
import com.example.tbcacademy.presentation.screen.feed.contract.FeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel<FeedState, FeedEvent, FeedSideEffect>(
    FeedState(
        stories = emptyList(),
        posts = emptyList(),
        isLoading = false,
        error = null
    )
) {
    override fun onEvent(event: FeedEvent) {
        when (event) {
            FeedEvent.LoadData,
            FeedEvent.RefreshData -> fetchData()
        }
    }

    private fun fetchData() {
        updateState { it.copy(isLoading = true) }
        emitSideEffect(FeedSideEffect.ShowLoading)

        viewModelScope.launch {
            try {
                val storiesResult = getStoriesUseCase()
                val postsResult = getPostsUseCase()
                val stories = if (storiesResult is Resource.Success) {
                    storiesResult.data ?: emptyList()
                } else {
                    emptyList()
                }

                val posts = if (postsResult is Resource.Success) {
                    postsResult.data ?: emptyList()
                } else {
                    emptyList()
                }

                val error = (storiesResult as? Resource.Error)?.errorMessage
                    ?: (postsResult as? Resource.Error)?.errorMessage

                updateState {
                    it.copy(
                        stories = stories,
                        posts = posts,
                        isLoading = false,
                        error = error
                    )
                }

                emitSideEffect(FeedSideEffect.HideLoading)

                if (error != null) {
                    emitSideEffect(FeedSideEffect.ShowError(message = error))
                }

            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, error = e.message) }
                emitSideEffect(FeedSideEffect.ShowError(message = e.message))
            }
        }
    }
}