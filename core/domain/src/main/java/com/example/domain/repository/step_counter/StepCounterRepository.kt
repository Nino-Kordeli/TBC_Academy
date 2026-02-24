package com.example.domain.repository.step_counter

import kotlinx.coroutines.flow.StateFlow

interface StepCounterRepository {
    val steps: StateFlow<Int>
    fun startCounting()
    fun stopCounting()
}