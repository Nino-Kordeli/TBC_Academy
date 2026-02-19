package com.example.impl.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.Green
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.White
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizState

@Composable
fun AccountCreatedStep(calculatedCalories: Int?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Account Created",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(90.dp))

        Text("Congratulations!", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            "Your custom plan is ready and you’re one step closer to your goal weight.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Your daily net goal is:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "$calculatedCalories",
            fontSize = 50.sp,
            color = Green
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = LightGray,
            modifier = Modifier.padding(horizontal = 54.dp, vertical = 22.dp)
        )
    }
}