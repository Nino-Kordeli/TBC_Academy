package com.example.impl.screen.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.api.AuthenticationNavKey
import com.example.core.navigation.Navigator
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.PrimaryPink
import com.example.designsystem.theme.White
import com.example.impl.screen.ProfileEvent
import com.example.impl.screen.ProfileSideEffect
import com.example.impl.screen.vm.ProfileViewModel
import com.example.ui.base.BaseScreen

@Composable
fun ProfileScreen(
    navigator: Navigator,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                ProfileSideEffect.NavigateToLogin -> {
                    navigator.navigate(AuthenticationNavKey.LoginNavKey)
                }
            }
        }
    ) { state, onEvent ->

        BackHandler() { }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(White, PrimaryBlue))),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileIcon()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = state.user,
                fontWeight = FontWeight.SemiBold,
                fontSize = 48.sp,
                color = White
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(R.drawable.ic_mail), contentDescription = "", Modifier.size(26.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = state.email, color = White, fontSize = 22.sp, fontWeight = FontWeight.W300)
            }

            Spacer(modifier = Modifier.height(120.dp))

            GlassButton(
                text = "Logout",
                icon = ImageVector.vectorResource(R.drawable.ic_logout),
                onClick = { onEvent(ProfileEvent.LogoutCLicked) }, // just send event
                modifier = Modifier.width(180.dp).height(70.dp)
            )
        }
    }
}

@Composable
fun ProfileIcon(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.default_user_image)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .border(8.dp, color = PrimaryPink, CircleShape)
            .size(180.dp),
    )
}

@Composable
fun GlassButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(
                Color.White.copy(alpha = 0.20f) // translucent surface
            )
            .border(
                width = 2.dp,
                color = Color.White.copy(alpha = 0.45f),
                shape = RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 28.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}