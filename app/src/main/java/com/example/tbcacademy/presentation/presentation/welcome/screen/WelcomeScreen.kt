package com.example.tbcacademy.presentation.presentation.welcome.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.presentation.navigation.Routes
import com.example.myapplication.presentation.welcome.contract.WelcomeEvent
import com.example.myapplication.presentation.welcome.contract.WelcomeSideEffect
import com.example.myapplication.presentation.welcome.vm.WelcomeViewModel
import com.example.tbcacademy.R

@Composable
fun WelcomeScreen(
    navigator: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                WelcomeSideEffect.NavigateToLogin -> navigator.navigate(Routes.LOGIN)
                WelcomeSideEffect.NavigateToRegister -> {navigator.navigate(Routes.REGISTER)}
            }
        }
    ) { state, onEvent ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_icon_logged_out),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(Modifier.width(20.dp))

                    Text(
                        text = "photo",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    ),
                    onClick = { onEvent(WelcomeEvent.LoginClicked) }
                ) {
                    Text("Login")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = { onEvent(WelcomeEvent.RegisterClicked)}
                ) {
                    Text("Register")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        navigator = rememberNavController()
    )
}