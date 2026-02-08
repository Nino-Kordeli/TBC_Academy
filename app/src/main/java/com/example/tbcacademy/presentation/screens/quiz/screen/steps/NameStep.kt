package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.screens.login.screen.OutlinedTextFieldWithInlineLabel
import com.example.tbcacademy.presentation.theme.LightGray
import com.example.tbcacademy.presentation.theme.White

@Composable
fun NameStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {

    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "First, what can we call you?",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )

        Text(
            text = "We'd like to get to know you.",
            fontSize = 15.sp,
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp),
        )

        Spacer(modifier = Modifier.size(75.dp))

        OutlinedTextFieldWithInlineLabel(
            value = name,
            onValueChange = { name = it },
            label = "Preferred first name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = "name"
        )

    }
}

@Preview
@Composable
fun NameStepPreview() {
    NameStep(
        state = QuizState(name = "Doll"),
        onEvent = {}
    )
}