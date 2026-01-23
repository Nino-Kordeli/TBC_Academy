package com.example.tbcacademy.presentation.orders.vm

import com.example.tbcacademy.domain.model.Order
import com.example.tbcacademy.domain.model.OrderStatus
import com.example.tbcacademy.domain.usecase.GetOrdersUseCase
import com.example.tbcacademy.presentation.orders.contract.OrdersEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    private lateinit var viewModel: OrdersViewModel
    private lateinit var getOrdersUseCase: GetOrdersUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val mockOrders = listOf(
        Order(
            id = 1,
            orderNumber = "1524",
            date = "13/05/2025",
            trackingNumber = "IK287368838",
            quantity = 2,
            subtotal = 110,
            status = OrderStatus.PENDING
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getOrdersUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load orders updates state`() = runTest {
        coEvery { getOrdersUseCase() } returns mockOrders
        viewModel = OrdersViewModel(getOrdersUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockOrders, state.orders)
    }

    @Test
    fun `loadOrders sets isLoading true then false`() = runTest {
        coEvery { getOrdersUseCase() } returns mockOrders
        viewModel = OrdersViewModel(getOrdersUseCase)

        assertTrue(viewModel.state.value.isLoading)

        advanceUntilIdle()
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(mockOrders, viewModel.state.value.orders)
    }

    @Test
    fun `refresh preserves local status changes`() = runTest {
        coEvery { getOrdersUseCase() } returns mockOrders
        viewModel = OrdersViewModel(getOrdersUseCase)
        advanceUntilIdle()

        viewModel.onEvent(OrdersEvent.ChangeOrderStatus(1, OrderStatus.CANCELED))
        viewModel.onEvent(OrdersEvent.Refresh)
        advanceUntilIdle()

        val updatedOrder = viewModel.state.value.orders.find { it.id == 1 }
        assertEquals(OrderStatus.CANCELED, updatedOrder?.status)
    }

    @Test
    fun `clicking change status updates order`() = runTest {
        coEvery { getOrdersUseCase() } returns mockOrders
        viewModel = OrdersViewModel(getOrdersUseCase)
        advanceUntilIdle()

        viewModel.onEvent(OrdersEvent.ChangeOrderStatus(1, OrderStatus.DELIVERED))

        val updatedOrder = viewModel.state.value.orders.first { it.id == 1 }
        assertEquals(OrderStatus.DELIVERED, updatedOrder.status)
    }
}
