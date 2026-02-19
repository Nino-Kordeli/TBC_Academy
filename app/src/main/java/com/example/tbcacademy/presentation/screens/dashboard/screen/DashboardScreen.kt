package com.example.tbcacademy.presentation.screens.dashboard.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.designsystem.theme.Green
import com.example.designsystem.theme.Pink40
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.model.CaloriesUiModel

@Composable
fun DashboardScreen(
    navController: NavController,
    goalCalories: Int,
    modifier: Modifier = Modifier
) {

    BackHandler { }

    val caloriesData = CaloriesUiModel(
        goal = goalCalories,
        food = 1000,
        exercise = 120
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {

        Header()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Today",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            DailyCaloriesCard(caloriesData)

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
            }
        }
    }
}

@Composable
private fun Header() {

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
}

@Composable
private fun ProfileIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun DailyCaloriesCard(data: CaloriesUiModel) {

    val remaining = data.goal - data.food + data.exercise
    val isOverGoal = data.food > data.goal + data.exercise

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {

        Column(modifier = Modifier.padding(24.dp)) {

            Text(
                text = "Calories",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(contentAlignment = Alignment.Center) {

                    MultiColorCircularProgress(
                        modifier = Modifier.size(120.dp),
                        goal = data.goal,
                        consumed = data.food,
                        burned = data.exercise
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = if (remaining >= 0) "$remaining" else "0",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isOverGoal) Color.Red else Color.Black
                        )

                        if (isOverGoal) {
                            Text(
                                text = "+${-remaining}",
                                fontSize = 14.sp,
                                color = Color.Red,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    StatRow(R.drawable.ic_flag, "Base Goal\n${data.goal}")
                    StatRow(R.drawable.ic_cutlery, "Food\n${data.food}")
                    StatRow(R.drawable.ic_fire, "Exercise\n${data.exercise}")
                }
            }
        }
    }
}

@Composable
private fun StatRow(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(text)
    }
}

@Composable
private fun MultiColorCircularProgress(
    modifier: Modifier = Modifier,
    goal: Int,
    consumed: Int,
    burned: Int,
    strokeWidth: Dp = 10.dp
) {

    Canvas(modifier = modifier) {

        val canvasSize = size.minDimension
        val stroke = strokeWidth.toPx()

        drawArc(
            color = Color.LightGray.copy(alpha = 0.3f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(stroke, cap = StrokeCap.Round),
            size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
        )

        val netCalories = consumed - burned
        val netProgress =
            (netCalories.toFloat() / goal).coerceIn(0f, 2f)

        if (consumed > 0) {
            drawArc(
                color = if (netCalories > goal)
                    Color(0xFFFF9800)
                else PrimaryBlue,
                startAngle = -90f,
                sweepAngle = 360f * netProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(stroke, cap = StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
            )
        }

        if (burned > 0) {
            drawArc(
                color = Green,
                startAngle = -90f + (360f * netProgress.coerceIn(0f, 1f)),
                sweepAngle = 360f * (burned.toFloat() / goal).coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(stroke, cap = StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
            )
        }
    }
}