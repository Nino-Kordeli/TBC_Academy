package com.example.impl.screens.login.screen

import androidx.compose.foundation.background
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
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.impl.screens.login.contract.LoginEvent
import com.example.impl.screens.login.contract.LoginSideEffect
import com.example.impl.screens.login.vm.LoginViewModel
import com.example.ui.base.BaseScreen
import com.example.ui.components.OutlinedTextFieldWithInlineLabel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onShowError: (String) -> Unit
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                LoginSideEffect.NavigateToHome ->
                    onNavigateHome()

                LoginSideEffect.NavigateToRegister ->
                    onNavigateToRegister()

                is LoginSideEffect.ShowError ->
                    onShowError(effect.message)
            }
        },
        modifier = Modifier
    ) { state, onEvent ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Log In",
                Modifier.padding(top = 34.dp),
                color = LightGray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(84.dp))

            OutlinedTextFieldWithInlineLabel(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                label = "Email Address",
                placeholder = "user@example.com",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextFieldWithInlineLabel(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                label = "Password",
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
                onClick = { onEvent(LoginEvent.LoginCLicked) },

                ) {
                Text(text = "Log In", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Forgot password?",
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(50.dp))

            Text(
                text = "OR",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(30.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(40.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults
                    .buttonColors(containerColor = White, contentColor = Black),
                onClick = { onEvent(LoginEvent.RegisterClicked) },

                ) {
                Text(text = "Register now!", fontWeight = FontWeight.Bold)
            }
        }
    }
}
