package com.example.impl.screens.add_food_details.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.theme.Green
import com.example.designsystem.theme.NeutralLightGray
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.domain.model.food.Food
import com.example.impl.screens.add_food_details.components.IngredientMass
import com.example.impl.screens.add_food_details.contract.AddFoodDetailsEvent
import com.example.impl.screens.add_food_details.vm.AddFoodDetailsViewModel
import com.example.ui.base.BaseScreen

@Composable
fun AddFoodDetailsScreen(
    viewModel: AddFoodDetailsViewModel = hiltViewModel(),
    food: Food
) {
    BaseScreen(modifier = Modifier, viewModel = viewModel) { state, onEvent ->
        LaunchedEffect(Unit) {
            onEvent(AddFoodDetailsEvent.FetchFoods)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {

            Text(
                text = "Toast Bread",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 20.dp, start = 16.dp)
            )

            HorizontalDivider(
                color = NeutralLightGray,
                thickness = 1.6.dp,
                modifier = Modifier.padding(top = 20.dp)
            )

            ItemRow("Meal", "Breakfast")
            ItemRow("Number of Servings", "3")
            ItemRow("Serving Size", "1 slice")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MultiColorCircularProgress(
                    modifier = Modifier.size(80.dp),
                    caloryAmount = 192,
                    carbs = 1,
                    fats = 1
                )

                IngredientMass("37.7g", "Carbs")
                IngredientMass("2.6g", "Fat")
                IngredientMass("5.9g", "Protein")
            }
        }
    }
}

@Composable
@Preview
fun AddFoodDetailsScreenPreview() {
    AddFoodDetailsScreen(
        food = Food("", "Toast Bread", 192, 1f, 1f, 1f, true)
    )
}

@Composable
fun ItemRow(
    label: String,
    secondaryLabel: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = White)
            .padding(end = 12.dp, start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = secondaryLabel,
            fontSize = 14.sp,
            color = PrimaryBlue
        )
    }
    HorizontalDivider(
        color = NeutralLightGray,
        thickness = 1.dp
    )
}

@Composable
private fun MultiColorCircularProgress(
    modifier: Modifier = Modifier,
    caloryAmount: Int,
    carbs: Int,
    fats: Int,
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

        val netCalories = carbs - fats
        val consumedProgress = (carbs.toFloat() / caloryAmount).coerceIn(0f, 2f)
        val burnedProgress = (fats.toFloat() / caloryAmount).coerceIn(0f, 1f)
        val netProgress = (netCalories.toFloat() / caloryAmount).coerceIn(0f, 2f)

        if (carbs > 0) {
            drawArc(
                color = if (netCalories > caloryAmount) Color(0xFFFF9800) else PrimaryBlue,
                startAngle = -90f,
                sweepAngle = 360f * netProgress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
        }

        if (fats > 0 && carbs > 0) {
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

        if (netCalories > caloryAmount) {
            val overProgress =
                ((netCalories - caloryAmount).toFloat() / caloryAmount).coerceAtMost(1f)
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