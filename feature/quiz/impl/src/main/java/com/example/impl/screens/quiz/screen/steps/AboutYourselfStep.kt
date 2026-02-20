package com.example.impl.screens.quiz.screen.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.White
import com.example.impl.screens.quiz.components.BorderedDropdownField
import com.example.impl.screens.quiz.contract.QuizEvent
import com.example.impl.screens.quiz.contract.QuizState
import com.example.ui.components.OutlinedTextFieldWithInlineLabel

@Composable
fun AboutYourselfStep(state: QuizState, onEvent: (QuizEvent) -> Unit) {
    var age by remember { mutableStateOf("") }

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

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "How old are you?",
            color = LightGray,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextFieldWithInlineLabel(
            value = age,
            onValueChange = { age = it },
            label = "Age",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = "",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.None,
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Where do you live?",
            color = LightGray,
            fontWeight = FontWeight.SemiBold
        )

        var selectedCountry by remember { mutableStateOf<String?>(null) }

        BorderedDropdownField(
            label = "Country",
            options = listOf("Georgia", "Turkey", "Poland", "Germany", "Japan", "Ireland"),
            selectedOption = selectedCountry,
            onOptionSelected = { selectedCountry = it }
        )
    }
}