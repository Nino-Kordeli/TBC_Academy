package com.example.tbcacademy.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<S, E, F>(initialState: S) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    protected val _effect = Channel<F>()
    val effect = _effect.receiveAsFlow()

    protected fun setState(reduce: S.() -> S) {
        _state.value = _state.value.reduce()
    }

    protected suspend fun postEffect(effect: F) {
        _effect.send(effect)
    }

    abstract fun onEvent(event: E)
}
