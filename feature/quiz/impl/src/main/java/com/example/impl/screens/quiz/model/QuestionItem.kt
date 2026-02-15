package com.example.impl.screens.quiz.model

data class QuestionItem(
    val id: Int,
    val primaryText: String,
    val secondaryText: String? = null,
    val selected: Boolean = false
)