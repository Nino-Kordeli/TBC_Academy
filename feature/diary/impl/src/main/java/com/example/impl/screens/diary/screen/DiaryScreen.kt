package com.example.impl.screens.diary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.R
import com.example.designsystem.theme.MilkyPink
import com.example.designsystem.theme.PrimaryColorPink
import com.example.designsystem.theme.VeryLightGray
import com.example.designsystem.theme.White
import com.example.domain.model.food.Food
import com.example.impl.screens.diary.vm.DiaryViewModel
import com.example.model.MealType
import com.example.ui.base.BaseScreen

@Composable
fun DiaryScreen(
    viewModel: DiaryViewModel = hiltViewModel(),
    navigateToAddFood: (MealType) -> Unit,
) {
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel
    ) { state, _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = VeryLightGray)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .height(40.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.ic_arrow_left),
                    contentDescription = ""
                )

                Text(text = "Today")

                Image(
                    painterResource(R.drawable.ic_arrow_right),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White)
                        .height(40.dp)
                ) {
                    Text(
                        text = "Calories remaining",
                        modifier = Modifier.padding(top = 18.dp, start = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painterResource(R.drawable.ic_arrow_right),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 22.dp, end = 24.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                        .height(76.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(
                        value = state.goalCalories.toString(),
                        label = "Goal",
                    )
                    StatItem(
                        value = "-",
                        label = "",
                    )
                    StatItem(
                        value = state.consumedCalories.toString(),
                        label = "Food",
                    )
                    StatItem(
                        value = "+",
                        label = "",
                    )
                    StatItem(
                        value = state.exerciseCalories.toString(),
                        label = "Exercise",
                    )
                    StatItem(
                        value = "=",
                        label = "",
                    )
                    StatItem(
                        value = state.remainingCalories.toString(),
                        label = "Remaining",
                        color = if (state.remainingCalories < 0) Color.Red else PrimaryColorPink
                    )
                }
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    FoodItem(
                        title = "Breakfast",
                        foods = state.breakfast,
                        mealType = MealType.BREAKFAST,
                        onAddFoodClick = { navigateToAddFood(it) }
                    )
                }

                item {
                    FoodItem(
                        title = "Lunch",
                        foods = state.lunch,
                        mealType = MealType.LUNCH,
                        onAddFoodClick = { navigateToAddFood(it) }
                    )
                }

                item {
                    FoodItem(
                        title = "Dinner",
                        foods = state.dinner,
                        mealType = MealType.DINNER,
                        onAddFoodClick = { navigateToAddFood(it) }
                    )
                }

                item {
                    FoodItem(
                        title = "Snacks",
                        foods = state.snacks,
                        mealType = MealType.SNACKS,
                        onAddFoodClick = { navigateToAddFood(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = if (label == "Remaining") FontWeight.Bold else FontWeight.Normal,
            color = color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun FoodItem(
    title: String,
    foods: List<Food>,
    mealType: MealType,
    onAddFoodClick: (MealType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .height(40.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            val totalCalories = foods.sumOf { it.calories }
            if (totalCalories > 0) {
                Text(
                    text = "$totalCalories",
                    fontSize = 14.sp,
                    color = PrimaryColorPink,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        foods.forEach { food ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = food.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${food.calories} cals",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(MilkyPink)
                    .fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .height(40.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ADD FOOD",
                color = PrimaryColorPink,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    onAddFoodClick(mealType)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}