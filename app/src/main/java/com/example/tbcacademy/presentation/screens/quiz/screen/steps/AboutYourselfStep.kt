package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizEvent
import com.example.tbcacademy.presentation.screens.quiz.contract.QuizState
import com.example.tbcacademy.presentation.screens.quiz.model.QuestionItem
import com.example.tbcacademy.presentation.screens.quiz.model.QuizStep
import com.example.tbcacademy.presentation.screens.quiz.model.SelectionType
import com.example.tbcacademy.presentation.theme.LightGray
import com.example.tbcacademy.presentation.theme.White

@Composable
fun AboutYourselfStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Text(
            text = "Tell us a little bit about yourself",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            maxLines = 2
        )

        Text(
            text = "Please select which sex we should use to calculate your calorie needs:",
            fontSize = 15.sp,
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.questions.forEach { question ->
                SingleChoiceItem(
                    question = question,
                    onSelect = {
                        onEvent(
                            QuizEvent.OnQuestionChecked(
                                question.id,
                                true
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutYourselfStepPreview() {
    AboutYourselfStep(
        state = QuizState(
            currentStep = QuizStep.GENDER_AGE,
            selectionType = SelectionType.SINGLE,
            questions = listOf(
                QuestionItem(
                    id = 1,
                    primaryText = "Male",
                    selected = true
                ),
                QuestionItem(
                    id = 2,
                    primaryText = "Female",
                    selected = false
                )
            )
        ),
        onEvent = {}
    )
}