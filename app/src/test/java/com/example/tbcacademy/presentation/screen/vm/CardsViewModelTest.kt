package com.example.tbcacademy.presentation.screen.vm

import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.domain.usecase.GetCardsUseCase
import com.example.tbcacademy.presentation.common.Resource
import com.example.tbcacademy.presentation.screen.contract.CardsEvent
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class CardsViewModelTest {

    private lateinit var viewModel: CardsViewModel
    private lateinit var getCardsUseCase: GetCardsUseCase

    private val testDispatcher = StandardTestDispatcher()

    private val mockCards = listOf(
        Cards(
            location = "Telavi, Georgia",
            altitudeM = 490,
            title = "Vineyard Infrastructure Project",
            image = "https://images.example.com/workspaces/telavi-vineyard-project.jpg",
            stars = 4,
            id = 1
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCardsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created then cards load successfully into state`() = runTest {

        coEvery { getCardsUseCase() } returns Resource.Success(mockCards)

        viewModel = CardsViewModel(getCardsUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertEquals(mockCards, state.cards)
        assertEquals(null, state.error)
    }

    @Test
    fun `when loading starts then loading flag is true then false after fetch`() = runTest {

        coEvery { getCardsUseCase() } returns Resource.Success(mockCards)

        viewModel = CardsViewModel(getCardsUseCase)

        assertTrue(viewModel.state.value.isLoading)

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `when retry event is triggered then cards are fetched again`() = runTest {

        coEvery { getCardsUseCase() } returns Resource.Success(mockCards)

        viewModel = CardsViewModel(getCardsUseCase)

        advanceUntilIdle()

        viewModel.onEvent(CardsEvent.Retry)

        advanceUntilIdle()

        assertEquals(mockCards, viewModel.state.value.cards)
    }

    @Test
    fun `when use case returns error then state shows error and empty cards`() = runTest {

        coEvery { getCardsUseCase() } returns Resource.Error("Something broke")

        viewModel = CardsViewModel(getCardsUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertEquals("Something broke", state.error)
        assertTrue(state.cards.isEmpty())
    }

    @Test
    fun `when use case returns empty list then state updates with no error`() = runTest {
        coEvery { getCardsUseCase() } returns Resource.Success(emptyList())

        viewModel = CardsViewModel(getCardsUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.cards.isEmpty())
        assertEquals(null, state.error)
    }

    @Test
    fun `when LoadCards event is triggered then loading flag is true`() = runTest {
        coEvery { getCardsUseCase() } returns Resource.Success(mockCards)

        viewModel = CardsViewModel(getCardsUseCase)
        viewModel.onEvent(CardsEvent.LoadCards)

        assertTrue(viewModel.state.value.isLoading)

        advanceUntilIdle()
        assertFalse(viewModel.state.value.isLoading)
    }
}
