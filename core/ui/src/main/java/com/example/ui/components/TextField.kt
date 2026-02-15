package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.NeutralDarkGrey
import com.example.designsystem.theme.NeutralGray
import com.example.designsystem.theme.White

@Composable
fun OutlinedTextFieldWithInlineLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String
) {
    Box(modifier = modifier) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {},
            placeholder = {
                Text(
                    text = placeholder,
                    color = NeutralGray
                )
            },
            singleLine = true
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = NeutralDarkGrey,
            modifier = Modifier
                .padding(start = 28.dp)
                .background(White)
                .padding(horizontal = 6.dp)
                .align(Alignment.TopStart)
        )
    }
}