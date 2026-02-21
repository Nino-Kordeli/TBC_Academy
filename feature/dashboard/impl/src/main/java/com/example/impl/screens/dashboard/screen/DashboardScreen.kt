package com.example.impl.screens.dashboard.screen

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core.navigation.Navigator
import com.example.designsystem.theme.Pink40
import com.example.designsystem.theme.White
import com.example.impl.screens.dashboard.components.DailyCaloriesCard
import com.example.impl.screens.dashboard.components.ProfileIcon
import com.example.impl.screens.dashboard.components.StepCounterRow
import com.example.impl.screens.dashboard.vm.DashboardViewModel
import com.example.impl.service.StepCounterService
import com.example.ui.base.BaseScreen

@Composable
fun DashboardScreen(
    onSearchClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
    navigator: Navigator
) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val intent = Intent(context, StepCounterService::class.java)
        context.startForegroundService(intent)
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
            val intent = Intent(context, StepCounterService::class.java)
            context.startForegroundService(intent)
        }
    }

    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onSideEffect = { }
    ) { state, onEvent ->
        BackHandler { }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                ProfileIcon(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "My Fitness Journey",
                        fontSize = 25.sp,
                        color = Pink40,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Today", fontSize = 30.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                DailyCaloriesCard(data = state.caloriesData)

                Spacer(modifier = Modifier.height(16.dp))

                StepCounterRow(
                    steps = state.steps,
                    calories = state.caloriesData.exercise
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) { }
            }
        }
    }
}