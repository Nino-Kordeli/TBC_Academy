package com.example.tbcacademy.presentation.screens.dashboard.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.model.CaloriesUiModel
import com.example.tbcacademy.presentation.navigation.Routes
import com.example.tbcacademy.presentation.theme.Green
import com.example.tbcacademy.presentation.theme.Pink40
import com.example.tbcacademy.presentation.theme.PrimaryBlue
import com.example.tbcacademy.presentation.theme.White
import com.example.tbcacademy.R

@Composable
fun DashboardScreen(
    navController: NavController,
    goalCalories: Int,
    modifier: Modifier = Modifier.fillMaxWidth()
) {

    val caloriesData = CaloriesUiModel(
        goal = goalCalories,
        food = 1000,
        exercise = 120
    )

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
            Text(
                text = "Today",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            DailyCaloriesCard(data = caloriesData)

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
            }
        }
    }
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
fun BottomBar(navController: NavController, hasSearch: Boolean) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (hasSearch) 160.dp else 80.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.vector_4),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (hasSearch) 160.dp else 80.dp)
                .align(Alignment.BottomCenter),
            colorFilter = ColorFilter.tint(PrimaryBlue)
        )

        if (hasSearch) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.60f)
                    .height(55.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 18.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .clickable { navController.navigate(Routes.SEARCH) },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_magnifying_glass),
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Search for food",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }
            }
        }

        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp),
            contentColor = White
        ) {

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.DASHBOARD) == true,
                onClick = {
                    if (currentRoute?.startsWith(Routes.DASHBOARD) == false) {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_dashboard),
                        contentDescription = "",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Dashboard",
                        fontSize = 12.sp,
                        fontWeight = if (currentRoute?.startsWith(Routes.DASHBOARD) == true)
                            FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = White,
                    selectedTextColor = White,
                    unselectedIconColor = White.copy(alpha = 0.7f),
                    unselectedTextColor = White.copy(alpha = 0.7f),
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = currentRoute == Routes.DIARY,
                onClick = {
                    navController.navigate(Routes.DIARY) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_diary),
                        contentDescription = "",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Diary",
                        fontSize = 12.sp,
                        fontWeight = if (currentRoute == Routes.DIARY)
                            FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = White,
                    selectedTextColor = White,
                    unselectedIconColor = White.copy(alpha = 0.7f),
                    unselectedTextColor = White.copy(alpha = 0.7f),
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = currentRoute == Routes.MORE,
                onClick = {
                    navController.navigate(Routes.MORE) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = "",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "More",
                        fontSize = 12.sp,
                        fontWeight = if (currentRoute == Routes.MORE)
                            FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = White,
                    selectedTextColor = White,
                    unselectedIconColor = White.copy(alpha = 0.7f),
                    unselectedTextColor = White.copy(alpha = 0.7f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun MultiColorCircularProgress(
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
            size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
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
                size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
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
                size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
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
                size = androidx.compose.ui.geometry.Size(canvasSize, canvasSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardWithBottomBarPreview() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController, hasSearch = true)
        }
    ) { padding ->

        DashboardScreen(
            navController = navController,
            goalCalories = 2200,
            modifier = Modifier.padding(padding)
        )
    }
}