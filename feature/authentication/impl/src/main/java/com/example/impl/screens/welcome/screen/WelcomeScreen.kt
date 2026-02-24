package com.example.impl.screens.welcome.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.impl.screens.welcome.contract.WelcomeEvent
import com.example.impl.screens.welcome.contract.WelcomeSideEffect
import com.example.impl.screens.welcome.vm.WelcomeViewModel
import com.example.ui.base.BaseScreen

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                WelcomeSideEffect.NavigateToLogin -> onLoginClick()
                WelcomeSideEffect.NavigateToRegister -> onRegisterClick()
            }
        },
        modifier = Modifier
    ) { _, onEvent ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "welcome to", fontSize = (24.sp)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "myfitnessjourney",
                fontSize = (38.sp),
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(38.dp))

            Image(
                painter = painterResource(id = R.drawable.image3),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(340.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(Modifier.height(34.dp))

            Text(
                text = "Ready to reach your goals?\nStart tracking your progress now!",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.padding(22.dp)
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onEvent(WelcomeEvent.RegisterClicked) },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = White,

                    )
            ) {
                Text(text = "Sign Up For Free", fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onEvent(WelcomeEvent.LoginClicked) },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = PrimaryBlue,
                )

            ) {
                Text(text = "Log In", fontSize = 18.sp)
            }

            Spacer(Modifier.height(34.dp))
        }
    }
}