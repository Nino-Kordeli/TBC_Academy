package com.example.impl.screens.quiz.screen.steps

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
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.White
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizState
import com.example.ui.components.OutlinedTextFieldWithInlineLabel

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
            value = state.name,
            onValueChange = {
                onEvent(QuizEvent.NameChanged(it))
            },
            label = "Preferred first name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = "name"
        )
    }
}