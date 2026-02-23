package com.example.impl.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.White
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizState

@Composable
fun BodyInfoStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Just a few more questions",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "How tall are you? (cm)",
            fontSize = 15.sp,
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.height,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            onValueChange = { onEvent(QuizEvent.HeightChanged(it)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "How much do you weight? (kg)",
            fontSize = 15.sp,
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.weight,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            onValueChange = { onEvent(QuizEvent.WeightChanged(it)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "What's your goal weight? (kg)",
            fontSize = 15.sp,
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.SemiBold,

            )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.goalWeight,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            onValueChange = { onEvent(QuizEvent.GoalWeightChanged(it)) }
        )
    }
}

@Composable
@Preview
fun BodyInfoStepPreview() {
    BodyInfoStep(
        state = QuizState(),
        onEvent = {}
    )
}