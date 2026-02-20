package com.example.impl.screens.dashboard.screen

import androidx.activity.compose.BackHandler
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
import com.example.impl.screens.dashboard.vm.DashboardViewModel
import com.example.ui.base.BaseScreen

@Composable
fun DashboardScreen(
    onSearchClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
    navigator: Navigator
){
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onSideEffect = { effect ->
        }
    ) { state, onEvent ->
        BackHandler() { }

        LaunchedEffect(Unit) {

        }

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

                DailyCaloriesCard(
                    data = state.caloriesData)

                Spacer(modifier = Modifier.height(24.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                }
            }
        }
    }
}

/*@Composable
fun BottomBar(navController: NavHostController,hasSearch: Boolean) {
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
}*/
