package com.example.tbcacademy.presentation.presentation.login.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.presentation.navigation.Routes
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.presentation.login.contract.LoginEvent
import com.example.tbcacademy.presentation.presentation.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.presentation.login.vm.LoginViewModel

@Composable
fun LoginScreen(
    navigator: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                LoginSideEffect.LoginToProfile ->
                    navigator.navigate(Routes.PROFILE)

                LoginSideEffect.ReturnToRegister ->
                    navigator.popBackStack()

                is LoginSideEffect.ShowError -> {
                    Toast.makeText(
                        navigator.context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    ) { state, onEvent ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 60.dp)
        ) {

            Image(
                modifier = Modifier
                    .clickable { onEvent(LoginEvent.BackClicked) }
                    .size(12.dp),
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Log in",
                fontSize = 36.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                label = { Text("Email") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
                onClick = { onEvent(LoginEvent.LoginClicked) }
            ) {
                Text("Next")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navigator = rememberNavController()
    )
}