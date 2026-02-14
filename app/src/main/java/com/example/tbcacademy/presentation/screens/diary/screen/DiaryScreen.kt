package com.example.tbcacademy.presentation.screens.diary.screen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.R
import com.example.tbcacademy.domain.model.food.Food
import com.example.tbcacademy.domain.model.food.MealType
import com.example.tbcacademy.presentation.screens.diary.contract.DiaryState
import com.example.tbcacademy.presentation.theme.MilkyPink
import com.example.tbcacademy.presentation.theme.PrimaryBlue
import com.example.tbcacademy.presentation.theme.VeryLightGray
import com.example.tbcacademy.presentation.theme.White

@Composable
fun DiaryScreen(
    state: DiaryState,
    onAddFoodClick: (MealType) -> Unit
) {
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
                    value = "1600",
                    label = "Goal",
                )
                StatItem(
                    value = "+",
                    label = "",
                )
                StatItem(
                    value = "0",
                    label = "Food",
                )
                StatItem(
                    value = "+",
                    label = "",
                )
                StatItem(
                    value = "0",
                    label = "Exercise",
                )
                StatItem(
                    value = "=",
                    label = "",
                )
                StatItem(
                    value = "1600",
                    label = "Remaining",
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
                    onAddFoodClick = onAddFoodClick
                )
            }

            item {
                FoodItem(
                    title = "Lunch",
                    foods = state.lunch,
                    mealType = MealType.LUNCH,
                    onAddFoodClick = onAddFoodClick
                )
            }

            item {
                FoodItem(
                    title = "Dinner",
                    foods = state.dinner,
                    mealType = MealType.DINNER,
                    onAddFoodClick = onAddFoodClick
                )
            }

            item {
                FoodItem(
                    title = "Snacks",
                    foods = state.snacks,
                    mealType = MealType.SNACKS,
                    onAddFoodClick = onAddFoodClick
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp
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
            Text(text = title)

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painterResource(R.drawable.ic_arrow_right),
                contentDescription = ""
            )
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
                color = PrimaryBlue,
                modifier = Modifier.clickable {
                    onAddFoodClick(mealType)
                }
            )
        }
    }
}

