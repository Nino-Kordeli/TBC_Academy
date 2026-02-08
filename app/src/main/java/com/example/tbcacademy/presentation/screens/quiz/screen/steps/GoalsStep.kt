package com.example.tbcacademy.presentation.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.tbcacademy.presentation.theme.PrimaryBlue
import com.example.tbcacademy.presentation.theme.White

@Composable
fun GoalsStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "In the past, what have been the barriers to losing weight?",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Select all that apply.",
            fontSize = 14.sp,
            color = LightGray
        )
        QuestionList(
            questions = state.questions,
            onCheckedChange = { id, checked ->
                onEvent(QuizEvent.OnQuestionChecked(id, checked))
            }
        )
    }
}

@Composable
fun QuestionList(
    questions: List<QuestionItem>,
    onCheckedChange: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(
            items = questions,
            key = { it.id }
        ) { item ->
            CheckboxItem(
                text = item.primaryText,
                checked = item.selected,
                onCheckedChange = { checked ->
                    onCheckedChange(item.id, checked)
                }
            )
        }
    }
}

@Composable
fun CheckboxItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .background(
                color = LightCreamBlue,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = (PrimaryBlue)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsStepPreview() {
    GoalsStep(
        state = QuizState(
            questions = listOf(
                QuestionItem(1, "Lack of time", selected = false),
                QuestionItem(2, "Stress eating", selected = false),
                QuestionItem(3, "Inconsistent routine", selected = false),
                QuestionItem(4, "Medical reasons", selected = false)
            )
        ),
        onEvent = {}
    )
}