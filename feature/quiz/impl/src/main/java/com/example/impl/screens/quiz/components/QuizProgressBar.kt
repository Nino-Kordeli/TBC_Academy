package com.example.impl.screens.quiz.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.PrimaryColorPink

@Composable
fun QuizProgressBar(progress: Float) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        label = "quiz_progress"
    )

    LinearProgressIndicator(
        progress = { animatedProgress.value },
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(6.dp),
        color = PrimaryColorPink
    )
}

@Preview
@Composable
fun QuizProgressBarPreview() {
    QuizProgressBar(progress = 0.5f)
}