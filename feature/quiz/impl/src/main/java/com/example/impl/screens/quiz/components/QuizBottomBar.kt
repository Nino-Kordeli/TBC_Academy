package com.example.impl.screens.quiz.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.designsystem.theme.WhiteBlue
import com.example.impl.screens.quiz.model.QuizStep

@Composable
fun QuizBottomBar(
    currentStep: QuizStep,
    isLastStep: Boolean,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        color = White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep != QuizStep.NAME) {
                Button(
                    onClick = onBack,
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhiteBlue
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_blue),
                        contentDescription = "Back",
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Text(if (isLastStep) "Finish" else "Next")
            }
        }
    }
}