package com.example.tbcacademy.presentation.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun <State, Event, SideEffect> BaseScreen(
    viewModel: BaseViewModel<State, Event, SideEffect>,
    onSideEffect: (SideEffect) -> Unit = {},
    content: @Composable (
        state: State,
        onEvent: (Event) -> Unit
    ) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            onSideEffect(effect)
        }
    }

    content(state, viewModel::onEvent)
}

