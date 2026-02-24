package com.example.impl.screens.add_food_details.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Green
import com.example.designsystem.theme.Orange
import com.example.designsystem.theme.PrimaryBlue

@Composable
internal fun NutritionCircularProgress(
    carbs: Float,
    fat: Float,
    protein: Float
) {
    Canvas(modifier = Modifier.size(100.dp)) {
        val canvasSize = size.minDimension
        val strokeWidthPx = 10.dp.toPx()

        drawArc(
            color = Color.LightGray.copy(alpha = 0.3f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            size = Size(canvasSize, canvasSize)
        )

        val total = carbs + fat + protein
        if (total > 0) {
            var currentAngle = -90f

            val carbsSweep = (carbs / total) * 360f
            drawArc(
                color = PrimaryBlue,
                startAngle = currentAngle,
                sweepAngle = carbsSweep,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
            currentAngle += carbsSweep

            val fatSweep = (fat / total) * 360f
            drawArc(
                color = Orange,
                startAngle = currentAngle,
                sweepAngle = fatSweep,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
            currentAngle += fatSweep

            val proteinSweep = (protein / total) * 360f
            drawArc(
                color = Green,
                startAngle = currentAngle,
                sweepAngle = proteinSweep,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = Size(canvasSize, canvasSize)
            )
        }
    }
}