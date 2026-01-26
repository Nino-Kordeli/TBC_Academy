package com.example.tbcacademy.presentation.screens.dashboard.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.model.CaloriesUiModel
import com.example.tbcacademy.presentation.theme.Pink40
import com.example.tbcacademy.presentation.theme.White

@Composable
fun DashboardScreen(
    navigator: NavController
) {

    val dummyCalories = CaloriesUiModel(
        goal = 2000,
        food = 900,
        exercise = 150
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {

        ProfileIcon(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

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
                    fontFamily = FontFamily.SansSerif
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Today",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            DailyCaloriesCard(data = dummyCalories)

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {

            }
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen(
        navigator = rememberNavController()
    )
}

@Composable
fun ProfileIcon(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_launcher_background)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun DailyCaloriesCard(data: CaloriesUiModel) {

    val remaining = data.goal - data.food + data.exercise
    val progress = (remaining.toFloat() / data.goal).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

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
                    CircularProgressIndicator(
                        progress = progress,
                        modifier = Modifier.size(120.dp),
                        color = if (progress == 0f) Color.Gray else Pink40,
                        strokeWidth = 10.dp
                    )

                    Text(
                        text = "$remaining",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Goal: ${data.goal}")
                    Text("Food: ${data.food}")
                    Text("Exercise: ${data.exercise}")
                }
            }
        }
    }
}
