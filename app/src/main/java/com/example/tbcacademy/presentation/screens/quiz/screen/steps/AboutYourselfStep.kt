package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.theme.White

@Composable
fun AboutYourselfStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) { }
}