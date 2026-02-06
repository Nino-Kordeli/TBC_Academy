package com.example.tbcacademy.feature.register.screen

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.feature.register.vm.RegisterViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: RegisterViewModel

    private val fakeFields = listOf(
        Field(1, "Email", "input", "email", true, true, "", ""),
        Field(2, "Birthday", "chooser", "", true, true, "", "")
    )

    @Before
    fun setup() {
        fakeViewModel = mockk<RegisterViewModel>(relaxed = true)
        every { fakeViewModel.state } returns MutableStateFlow(Resource.Success(fakeFields))
    }

    @Test
    fun screenShowsFields() {
        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Email *", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Birthday *", useUnmergedTree = true).assertExists()
    }

    @Test
    fun registerButtonExists() {
        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNode(
            hasText("Register") and hasClickAction()
        ).assertExists()
    }

    @Test
    fun showsLoadingState() {
        every { fakeViewModel.state } returns MutableStateFlow(
            Resource.Loader(isLoading = true)
        )

        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Companion.Indeterminate))
            .assertExists()
    }

    @Test
    fun showsErrorState() {
        every { fakeViewModel.state } returns MutableStateFlow(
            Resource.Error("Test error message")
        )

        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Error loading fields: Test error message")
            .assertExists()
    }

    @Test
    fun headerDisplaysCorrectly() {
        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithText("E-Auth").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Register").assertCountEquals(2)
    }

    @Test
    fun registerButtonIsClickable() {
        composeTestRule.setContent {
            RegisterScreen(
                navigator = rememberNavController(),
                viewModel = fakeViewModel
            )
        }

        composeTestRule.waitForIdle()

        // Click the Register button
        composeTestRule.onNode(
            hasText("Register") and hasClickAction()
        ).performClick()

        // Verify button still exists after click
        composeTestRule.onNode(
            hasText("Register") and hasClickAction()
        ).assertExists()
    }
}