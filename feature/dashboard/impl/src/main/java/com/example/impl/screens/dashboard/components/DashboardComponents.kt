package com.example.impl.screens.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.R
import com.example.designsystem.theme.Green
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.impl.screens.dashboard.model.CaloriesUiModel

@Composable
internal fun ProfileIcon(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.default_user_image)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
internal fun DailyCaloriesCard(data: CaloriesUiModel) {

    val remaining = data.goal - data.food + data.exercise
    val isOverGoal = data.food > data.goal + data.exercise

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
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

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(end = 14.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_flag),
                            contentDescription = null,
                            Modifier.size(20.dp)
                        )

                        Text("Base Goal\n${data.goal}")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_cutlery),
                            contentDescription = null,
                            Modifier.size(20.dp)
                        )

                        Text("Food\n${data.food}")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_fire),
                            contentDescription = null,
                            Modifier.size(20.dp)
                        )

                        Text("Exercise\n${data.exercise}")
                    }
                }

            }
        }
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
        val strokeWidthPx = strokeWidth.toPx()

        drawArc(
            color = Color.LightGray.copy(alpha = 0.3f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            size = Size(canvasSize, canvasSize)
        )

        val netCalories = consumed - burned
        val consumedProgress = (consumed.toFloat() / goal).coerceIn(0f, 2f)
        val burnedProgress = (burned.toFloat() / goal).coerceIn(0f, 1f)
        val netProgress = (netCalories.toFloat() / goal).coerceIn(0f, 2f)

        if (consumed > 0) {
            drawArc(
                color = if (netCalories > goal) Color(0xFFFF9800) else PrimaryBlue,
                startAngle = -90f,
                sweepAngle = 360f * netProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
        }

        if (burned > 0 && consumed > 0) {
            val greenStartAngle = -90f + (360f * netProgress.coerceIn(0f, 1f))
            drawArc(
                color = Green,
                startAngle = greenStartAngle,
                sweepAngle = 360f * burnedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
        }

        if (netCalories > goal) {
            val overProgress = ((netCalories - goal).toFloat() / goal).coerceAtMost(1f)
            drawArc(
                color = Color.Red,
                startAngle = -90f,
                sweepAngle = 360f * overProgress,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
        }
    }
}

@Composable
fun StepCounterRow(
    steps: Int,
    calories: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        StepCard(
            modifier = Modifier
                .weight(1f)
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_step),
                contentDescription = "Steps",
                modifier = Modifier.size(28.dp)
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Steps",
                    fontSize = 12.sp,
                    color = LightGray
                )

                Text(
                    text = steps.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        StepCard(
            modifier = Modifier
                .weight(1f)
                .height(120.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fire),
                    contentDescription = "Exercise",
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "$calories kcal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Exercise",
                fontSize = 12.sp,
                color = LightGray
            )
        }
    }
}
@Composable
private fun StepCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}