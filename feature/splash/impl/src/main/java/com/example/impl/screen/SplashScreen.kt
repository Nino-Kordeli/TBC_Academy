package com.example.impl.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.example.designsystem.theme.PrimaryColorPink
import com.example.impl.components.AnimatedDivider
import com.example.impl.components.AnimatedDumbbell
import com.example.impl.components.AnimatedSubtitle
import com.example.impl.components.AnimatedTitle
import com.example.impl.contract.SplashScreenSideEffect
import com.example.impl.vm.SplashScreenViewModel
import com.example.ui.base.BaseScreen

@Composable
fun SplashScreen(
    viewmodel: SplashScreenViewModel = hiltViewModel(),
    onNavigate: (NavKey) -> Unit
) {
    BaseScreen(
        viewModel = viewmodel,
        applySystemBarsPadding = false,
        onSideEffect = { effect ->
            when (effect) {
                is SplashScreenSideEffect.Navigate -> onNavigate(effect.navKey)
            }
        }
    ) { _, _ ->
        SplashScreenContent()
    }
}

@Composable
private fun SplashScreenContent() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorPink),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedDumbbell(visible = visible)

        Spacer(Modifier.height(18.dp))

        AnimatedTitle(visible = visible)

        Spacer(Modifier.height(18.dp))

        AnimatedDivider(visible = visible)

        Spacer(Modifier.height(24.dp))

        AnimatedSubtitle(visible = visible)
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent()
}
