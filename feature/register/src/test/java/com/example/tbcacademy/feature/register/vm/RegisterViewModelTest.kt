package com.example.tbcacademy.feature.register.vm

import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.core.domain.usecase.GetRegisterFieldsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest : BaseTest() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var getFieldsUseCase: GetRegisterFieldsUseCase

    private val mockFields = listOf(
        Field(
            id = 1,
            hint = "UserName",
            type = "input",
            keyboard = "text",
            required = false,
            isActive = true,
            icon = "https://jemala.png",
            value = ""
        ),
        Field(
            id = 2,
            hint = "Email",
            type = "input",
            keyboard = "text",
            required = true,
            isActive = true,
            icon = "https://jemala.png",
            value = ""
        ),
        Field(
            id = 3,
            hint = "Phone",
            type = "input",
            keyboard = "number",
            required = true,
            isActive = true,
            icon = "https://jemala.png",
            value = ""
        )
    )

    @Before
    override fun setup() {
        super.setup()
        getFieldsUseCase = mockk()
    }

    @Test
    fun `initial state should be loading`() {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)

        assertTrue(viewModel.state.value is Resource.Loader)
        assertTrue((viewModel.state.value as Resource.Loader).isLoading)
    }

    @Test
    fun `loadFields should emit success with fields when use case succeeds`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is Resource.Success)

        val fields = (state as Resource.Success).data
        assertEquals(3, fields.size)
        assertEquals("UserName", fields[0].hint)
        assertEquals("Email", fields[1].hint)
        assertEquals("Phone", fields[2].hint)

        coVerify(exactly = 1) { getFieldsUseCase() }
    }

    @Test
    fun `loadFields should emit error when use case emits error`() = runTest {
        val errorMessage = "Network error"
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Error(errorMessage))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is Resource.Error)
        assertEquals(errorMessage, (state as Resource.Error).message)
    }

    @Test
    fun `updateValue should update specific field value`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        viewModel.updateValue(2, "test@example.com")

        val state = viewModel.state.value as Resource.Success
        val fields = state.data

        assertEquals("test@example.com", fields[1].value)
        assertEquals("", fields[0].value)
        assertEquals("", fields[2].value)
    }

    @Test
    fun `updateValue should not update if field id doesn't exist`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        viewModel.updateValue(999, "some value")

        val state = viewModel.state.value as Resource.Success
        state.data.forEach {
            assertEquals("", it.value)
        }
    }

    @Test
    fun `updateValue should update multiple fields correctly`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        viewModel.updateValue(1, "john_doe")
        viewModel.updateValue(2, "john@example.com")
        viewModel.updateValue(3, "1234567890")

        val fields = (viewModel.state.value as Resource.Success).data

        assertEquals("john_doe", fields[0].value)
        assertEquals("john@example.com", fields[1].value)
        assertEquals("1234567890", fields[2].value)
    }

    @Test
    fun `loadFields can be called manually to refresh data`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        viewModel.loadFields()
        advanceUntilIdle()

        coVerify(exactly = 2) { getFieldsUseCase() }
    }

    @Test
    fun `state should preserve field values after update`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        viewModel.updateValue(1, "test_user")
        viewModel.updateValue(2, "test@test.com")

        val state = viewModel.state.value as Resource.Success

        assertEquals("test_user", state.data[0].value)
        assertEquals("test@test.com", state.data[1].value)
    }

    @Test
    fun `all fields should have empty values initially after loading`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value as Resource.Success
        state.data.forEach {
            assertEquals("", it.value)
        }
    }

    @Test
    fun `required fields should maintain required flag after loading`() = runTest {
        coEvery { getFieldsUseCase() } returns flowOf(Resource.Success(mockFields))

        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value as Resource.Success

        assertFalse(state.data[0].required)
        assertTrue(state.data[1].required)
        assertTrue(state.data[2].required)
    }
}
