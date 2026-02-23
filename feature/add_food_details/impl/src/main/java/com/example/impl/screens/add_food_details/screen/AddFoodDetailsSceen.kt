    package com.example.impl.screens.add_food_details.screen

    import androidx.compose.foundation.Canvas
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
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material.icons.filled.Remove
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.HorizontalDivider
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
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
    import androidx.compose.ui.unit.Dp
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
    import com.example.designsystem.theme.Green
    import com.example.designsystem.theme.NeutralLightGray
    import com.example.designsystem.theme.Orange
    import com.example.designsystem.theme.PrimaryBlue
    import com.example.designsystem.theme.White
    import com.example.domain.model.food.Food
    import com.example.impl.screens.add_food_details.components.IngredientMass
    import com.example.impl.screens.add_food_details.contract.AddFoodDetailsEvent
    import com.example.impl.screens.add_food_details.contract.AddFoodDetailsSideEffect
    import com.example.impl.screens.add_food_details.vm.AddFoodDetailsViewModel
    import com.example.model.MealType
    import com.example.ui.base.BaseScreen
    import kotlin.math.roundToInt

    @Composable
    fun AddFoodDetailsScreen(
        viewModel: AddFoodDetailsViewModel = hiltViewModel(),
        food: Food,
        mealType: MealType,
        onNavigateBack: () -> Unit = {}
    ) {
        BaseScreen(
            modifier = Modifier,
            viewModel = viewModel,
            onSideEffect = { effect ->
                when (effect) {
                    AddFoodDetailsSideEffect.NavigateBack -> onNavigateBack()
                }
            }
        ) { state, onEvent ->

            LaunchedEffect(food, mealType) {
                onEvent(AddFoodDetailsEvent.LoadFood(food = food, mealType = mealType))
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.food != null) {
                val currentFood = state.food
                val servings = state.numberOfServings

                // Calculate nutrition per serving (100g = 1 serving)
                val caloriesPerServing = currentFood.calories * servings
                val carbsPerServing = currentFood.carbs * servings
                val fatPerServing = currentFood.fat * servings
                val proteinPerServing = currentFood.protein * servings

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    Text(
                        text = currentFood.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 20.dp, start = 16.dp)
                    )

                    HorizontalDivider(
                        color = NeutralLightGray,
                        thickness = 1.6.dp,
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    ItemRow("Meal", mealType.name.lowercase().replaceFirstChar { it.uppercase() })

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
                            text = "Number of Servings",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    if (servings > 1) {
                                        onEvent(AddFoodDetailsEvent.ServingsChanged(servings - 1))
                                    }
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = if (servings > 1) PrimaryBlue else Color.LightGray,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        tint = White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            Text(
                                text = servings.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )

                            IconButton(
                                onClick = {
                                    onEvent(AddFoodDetailsEvent.ServingsChanged(servings + 1))
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(PrimaryBlue, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        color = NeutralLightGray,
                        thickness = 1.dp
                    )

                    ItemRow("Serving Size", "100g")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(120.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                        ) {
                            NutritionCircularProgress(
                                modifier = Modifier.size(100.dp),
                                calories = caloriesPerServing.toFloat(),
                                carbs = carbsPerServing,
                                fat = fatPerServing,
                                protein = proteinPerServing
                            )

                            Text(
                                text = caloriesPerServing.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IngredientMass(
                                amount = "${carbsPerServing.roundToInt()}g",
                                label = "Carbs"
                            )
                            IngredientMass(
                                amount = "${fatPerServing.roundToInt()}g",
                                label = "Fat"
                            )
                            IngredientMass(
                                amount = "${proteinPerServing.roundToInt()}g",
                                label = "Protein"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { onEvent(AddFoodDetailsEvent.SaveFood) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text(
                            text = "Add to Diary",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else if (state.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.error,
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onNavigateBack() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
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
    private fun NutritionCircularProgress(
        modifier: Modifier = Modifier,
        calories: Float,
        carbs: Float,
        fat: Float,
        protein: Float,
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