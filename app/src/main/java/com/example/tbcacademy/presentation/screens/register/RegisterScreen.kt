package com.example.tbcacademy.presentation.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.theme.Black
import com.example.tbcacademy.presentation.theme.LightGray
import com.example.tbcacademy.presentation.theme.NeutralGray
import com.example.tbcacademy.presentation.theme.PrimaryBlue
import com.example.tbcacademy.presentation.theme.White

@Composable
fun RegisterScreen() {

    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Register",
            Modifier.padding(top = 34.dp),
            color = LightGray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(84.dp))

        OutlinedTextFieldWithInlineLabel(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            placeholder = "user@example.com",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextFieldWithInlineLabel(
            value = email,
            onValueChange = { email = it },
            label = "Password",
            placeholder = "••••••••••••",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextFieldWithInlineLabel(
            value = email,
            onValueChange = { email = it },
            label = "Repeat password",
            placeholder = "••••••••••••",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(40.dp),
            colors = ButtonDefaults
                .buttonColors(containerColor = PrimaryBlue),
            onClick = {},

            ) {
            Text(text = "Register", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Already have an account?",
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    RegisterScreen()
}

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
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 28.dp)
                .background(White)
                .padding(horizontal = 6.dp)
                .align(Alignment.TopStart)
        )
    }
}