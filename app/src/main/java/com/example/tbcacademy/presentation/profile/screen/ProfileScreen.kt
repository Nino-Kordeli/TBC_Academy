package com.example.tbcacademy.presentation.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.navigation.Routes
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.profile.contract.ProfileEvent
import com.example.tbcacademy.presentation.profile.contract.ProfileSideEffect
import com.example.tbcacademy.presentation.profile.vm.ProfileViewModel

@Composable
fun ProfileScreen(
    navigator: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                ProfileSideEffect.ProfileToLogin -> navigator.navigate(Routes.LOGIN) {
                    popUpTo(0)
                }
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.height(40.dp))

                    Text(
                        text = "UserEmail@gmail.com",
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(40.dp))

                    Button(
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        onClick = { onEvent(ProfileEvent.LogoutClicked) }
                    ) {
                        Text("Logout")
                    }
                }

            }
        }

    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        navigator = rememberNavController()
    )
}