package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.designsystem.theme.White

@Composable
fun WeeklyGoalStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Text(
            text = "What is your weekly goal",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            maxLines = 2
        )

        state.questions.forEach { question ->
            SingleChoiceItem(
                question = question,
                onSelect = {
                    onEvent(QuizEvent.OnQuestionChecked(question.id, true))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WeeklyGoalStepPreview() {
    WeeklyGoalStep(
        state = QuizState(
            questions = listOf(
                QuestionItem(1, "Lose 0.25 kg per week","Recommended"),
                QuestionItem(2, "Lose 0.50 kg per week"),
                QuestionItem(3, "Lose 0.75 kg per week"),
                QuestionItem(4, "Lose 1 kg per week"),
            )
        ),
        onEvent = {}
    )
}
