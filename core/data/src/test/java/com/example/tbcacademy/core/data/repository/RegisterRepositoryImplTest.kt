package com.example.tbcacademy.core.data.repository

import app.cash.turbine.test
import com.example.tbcacademy.core.data.HandleResponse
import com.example.tbcacademy.core.data.dto.FieldDto
import com.example.tbcacademy.core.data.remote.RegisterApi
import com.example.tbcacademy.core.domain.common.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterRepositoryImplTest {

    private val mockApi = mockk<RegisterApi>()
    private val mockHandleResponse = mockk<HandleResponse>()

    private lateinit var repository: RegisterRepositoryImpl

    @Test
    fun `success - flattens and maps fields`() = runTest {

        val rawNested = listOf(
            listOf(
                FieldDto(
                    id = 1,
                    hint = "Email",
                    type = "input",
                    keyboard = "text",
                    required = true,
                    isActive = true,
                    icon = "icon1"
                ),
                FieldDto(
                    id = 2,
                    hint = "Phone",
                    type = "input",
                    keyboard = "number",
                    required = true,
                    isActive = true,
                    icon = "icon2"
                )
            ),
            listOf(
                FieldDto(
                    id = 3,
                    hint = "Birthday",
                    type = "input",
                    keyboard = "date",
                    required = false,
                    isActive = true,
                    icon = "icon3"
                )
            )
        )
        coEvery {
            mockHandleResponse.safeApiCall<List<List<FieldDto>>>(any())
        } returns flowOf(Resource.Success(rawNested))

        repository = RegisterRepositoryImpl(
            mockApi,
            mockHandleResponse
        )

        repository.getRegisterFields().test {

            val emission = awaitItem()

            assertTrue(emission is Resource.Success)

            val fields = (emission as Resource.Success).data

            assertEquals(3, fields.size)
            assertEquals("Email", fields[0].hint)
            assertEquals("Phone", fields[1].hint)
            assertEquals("Birthday", fields[2].hint)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error is propagated`() = runTest {

        val message = "Server down"

        coEvery {
            mockHandleResponse.safeApiCall<List<List<FieldDto>>>(any())
        } returns
                flowOf(Resource.Error(message))

        repository = RegisterRepositoryImpl(mockApi, mockHandleResponse)

        repository.getRegisterFields().test {

            val emission = awaitItem()

            assertTrue(emission is Resource.Error)
            assertEquals(message, (emission as Resource.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loading is preserved`() = runTest {

        coEvery {
            mockHandleResponse.safeApiCall<List<List<FieldDto>>>(any())
        } returns
                flowOf(Resource.Loader(true))

        repository = RegisterRepositoryImpl(mockApi, mockHandleResponse)

        repository.getRegisterFields().test {

            val emission = awaitItem()

            assertTrue(emission is Resource.Loader)
            assertTrue((emission as Resource.Loader).isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
