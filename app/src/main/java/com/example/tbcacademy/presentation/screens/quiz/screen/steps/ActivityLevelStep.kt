package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.tbcacademy.presentation.theme.LightCreamBlue
import com.example.tbcacademy.presentation.theme.LightGray
import com.example.tbcacademy.presentation.theme.White

@Composable
fun ActivityLevelStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "what is your baseline activity \nlevel?",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            maxLines = 2
        )

        Text(
            text = "Not including workouts - we count that separately.",
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = LightGray,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Select all that apply.",
            fontSize = 14.sp,
            color = LightGray
        )
        state.questions.forEach { question ->
            SingleChoiceItem(
                question = question,
                onSelect = {
                    onEvent(QuizEvent.OnQuestionChecked(question.id, true))
                }
            )
        }

    }
}

@Composable
fun SingleChoiceItem(
    question: QuestionItem,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(LightCreamBlue, RoundedCornerShape(10.dp))
            .clickable { onSelect() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(question.primaryText, fontWeight = FontWeight.SemiBold)

            question.secondaryText?.let {
                Spacer(Modifier.height(4.dp))
                Text(it, fontSize = 12.sp, color = LightGray)
            }
        }

        RadioButton(
            selected = question.selected,
            onClick = onSelect
        )
    }
}

@Composable
@Preview
fun ActivityLevelStepPreview() {
    ActivityLevelStep(
        state = QuizState(
            questions = listOf(
                QuestionItem(1, "Not Active","barely active not moving a lot", selected = false),
                QuestionItem(2, "Not Active","barely active not moving a lot",selected = false),
                QuestionItem(3, "Not Active","barely active not moving a lot", selected = false),
                QuestionItem(4, "Not Active","barely active not moving a lot", selected = false)
            )
        ),
        onEvent = {}
    )
}

