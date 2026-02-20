package com.example.impl.screens.quiz.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.impl.screens.quiz.model.QuestionItem
import com.example.impl.screens.quiz.screen.steps.CheckboxItem

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