package com.example.tbcacademy.presentation.screen.register.vm

import com.example.tbcacademy.domain.model.Field
import com.example.tbcacademy.domain.usecase.GetRegisterFieldsUseCase
import com.example.tbcacademy.presentation.common.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        // Given
        coEvery { getFieldsUseCase() } returns mockFields

        // When
        viewModel = RegisterViewModel(getFieldsUseCase)

        // Then
        assertTrue(viewModel.state.value is Resource.Loader)
        assertTrue((viewModel.state.value as Resource.Loader).isLoading)
    }

    @Test
    fun `loadFields should emit success with fields when use case succeeds`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields

        // When
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // Then
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
    fun `loadFields should emit error when use case throws exception`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getFieldsUseCase() } throws Exception(errorMessage)

        // When
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is Resource.Error)
        assertEquals(errorMessage, (state as Resource.Error).message)
    }

    @Test
    fun `updateValue should update specific field value`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // When
        viewModel.updateValue(2, "test@example.com")

        // Then
        val state = viewModel.state.value
        assertTrue(state is Resource.Success)

        val fields = (state as Resource.Success).data
        assertEquals("test@example.com", fields[1].value)
        assertEquals("", fields[0].value)
        assertEquals("", fields[2].value)
    }

    @Test
    fun `updateValue should not update if field id doesn't exist`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // When
        viewModel.updateValue(999, "some value")

        // Then
        val state = viewModel.state.value
        assertTrue(state is Resource.Success)

        val fields = (state as Resource.Success).data
        fields.forEach { field ->
            assertEquals("", field.value)
        }
    }

    @Test
    fun `updateValue should update multiple fields correctly`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // When
        viewModel.updateValue(1, "john_doe")
        viewModel.updateValue(2, "john@example.com")
        viewModel.updateValue(3, "1234567890")

        // Then
        val state = viewModel.state.value
        assertTrue(state is Resource.Success)

        val fields = (state as Resource.Success).data
        assertEquals("john_doe", fields[0].value)
        assertEquals("john@example.com", fields[1].value)
        assertEquals("1234567890", fields[2].value)
    }

    @Test
    fun `loadFields can be called manually to refresh data`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // When
        viewModel.loadFields()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getFieldsUseCase() }
    }

    @Test
    fun `state should preserve field values after update`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // When
        viewModel.updateValue(1, "test_user")
        viewModel.updateValue(2, "test@test.com")

        // Then
        val state = viewModel.state.value as Resource.Success
        assertEquals("test_user", state.data[0].value)
        assertEquals("test@test.com", state.data[1].value)
    }

    @Test
    fun `all fields should have empty values initially after loading`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields

        // When
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value as Resource.Success
        state.data.forEach { field ->
            assertEquals("", field.value)
        }
    }

    @Test
    fun `required fields should maintain required flag after loading`() = runTest {
        // Given
        coEvery { getFieldsUseCase() } returns mockFields

        // When
        viewModel = RegisterViewModel(getFieldsUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value as Resource.Success
        assertFalse(state.data[0].required)
        assertTrue(state.data[1].required)
        assertTrue(state.data[2].required)  
    }
}