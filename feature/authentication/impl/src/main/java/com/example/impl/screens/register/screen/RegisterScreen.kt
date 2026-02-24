package com.example.impl.screens.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.PrimaryColorPink
import com.example.designsystem.theme.White
import com.example.impl.screens.register.contract.RegisterEvent
import com.example.impl.screens.register.contract.RegisterSideEffect
import com.example.impl.screens.register.vm.RegisterViewModel
import com.example.ui.base.BaseScreen
import com.example.ui.components.OutlinedTextFieldWithInlineLabel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateQuiz: () -> Unit,
    onShowError: (String) -> Unit
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                RegisterSideEffect.NavigateToHome -> onNavigateQuiz()
                RegisterSideEffect.NavigateToLogin -> onNavigateToLogin()
                is RegisterSideEffect.ShowError ->
                    onShowError(effect.message)
            }
        },
        modifier = Modifier
    ) { state, onEvent ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(horizontal = 16.dp),
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
                value = state.email,
                onValueChange = {
                    onEvent(RegisterEvent.EmailChanged(it))
                },
                label = "Email Address",
                placeholder = "user@example.com"
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextFieldWithInlineLabel(
                value = state.password,
                onValueChange = {
                    onEvent(RegisterEvent.PasswordChanged(it))
                },
                label = "Password",
                placeholder = "••••••••"
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextFieldWithInlineLabel(
                value = state.repeatPassword,
                onValueChange = {
                    onEvent(RegisterEvent.RepeatPasswordChanged(it))
                },
                label = "Repeat Password",
                placeholder = "••••••••"
            )

            Spacer(Modifier.height(40.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(40.dp),
                colors = ButtonDefaults
                    .buttonColors(containerColor = PrimaryColorPink),
                onClick = { onEvent(RegisterEvent.RegisterClicked) },

                ) {
                Text(text = "Register", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Already have an account?",
                color = PrimaryColorPink,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    onNavigateToLogin()
                }
            )
        }
    }
}
