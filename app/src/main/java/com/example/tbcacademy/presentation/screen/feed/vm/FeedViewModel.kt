package com.example.tbcacademy.presentation.screen.feed.vm

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.domain.usecase.GetPostsUseCase
import com.example.tbcacademy.domain.usecase.GetStoriesUseCase
import com.example.tbcacademy.presentation.common.BaseViewModel
import com.example.tbcacademy.presentation.common.Resource
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
    FeedState()
) {

    init {
        fetchData()
    }

    private fun fetchData() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            val storiesResult = getStoriesUseCase()
            val postsResult = getPostsUseCase()

            val stories = (storiesResult as? Resource.Success)?.data.orEmpty()
            val posts = (postsResult as? Resource.Success)?.data.orEmpty()

            val error =
                (storiesResult as? Resource.Error)?.message
                    ?: (postsResult as? Resource.Error)?.message

            updateState {
                it.copy(
                    stories = stories,
                    posts = posts,
                    isLoading = false
                )
            }

            if (error != null) {
                emitSideEffect(FeedSideEffect.ShowError(error))
            }
        }
    }

    override fun onEvent(event: FeedEvent) {
    }
}
