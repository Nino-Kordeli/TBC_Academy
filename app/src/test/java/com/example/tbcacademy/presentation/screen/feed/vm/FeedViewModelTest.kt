package com.example.tbcacademy.presentation.screen.feed.vm

import app.cash.turbine.test
import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.model.Story
import com.example.tbcacademy.domain.usecase.GetPostsUseCase
import com.example.tbcacademy.domain.usecase.GetStoriesUseCase
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.screen.feed.contract.FeedSideEffect
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FeedViewModelTest : BaseTest() {

    private lateinit var getStoriesUseCase: GetStoriesUseCase
    private lateinit var getPostsUseCase: GetPostsUseCase
    private lateinit var viewModel: FeedViewModel

    @Before
    override fun setup() {
        super.setup()
        getStoriesUseCase = mockk()
        getPostsUseCase = mockk()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given use cases return data, When LoadData event is triggered, Then state is updated`() = runTest {
        // Given
        val fakeStories = listOf(Story(1, "Story One", "url1"))
        val fakePosts = listOf(Post(
            id = 1,
            avatar = "urlAvatar",
            postDate = 123456789L,
            firstName = "John",
            lastName = "Doe",
            images = emptyList(),
            commentsCount = 24,
            likesCount = 128,
            postDesc = "Hello world",
            canComment = true,
            canPostPhoto = true
        ))

        coEvery { getStoriesUseCase() } returns Resource.Success(fakeStories)
        coEvery { getPostsUseCase() } returns Resource.Success(fakePosts)

        // When
        viewModel = FeedViewModel(getStoriesUseCase, getPostsUseCase)

        advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(fakeStories, state.stories)
            assertEquals(fakePosts, state.posts)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given use cases fail, When LoadData event is triggered, Then ShowError side effect is emitted`() = runTest {
        // Given
        coEvery { getStoriesUseCase() } returns Resource.Error("Network failed")
        coEvery { getPostsUseCase() } returns Resource.Error("Network failed")

        // When
        viewModel = FeedViewModel(getStoriesUseCase, getPostsUseCase)

        // Then
        viewModel.sideEffect.test {
            val effect = awaitItem()
            assert(effect is FeedSideEffect.ShowError)
            assertEquals("Network failed", (effect as FeedSideEffect.ShowError).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
