package com.example.tbcacademy.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tbcacademy.presentation.common.BottomNavItem
import com.example.tbcacademy.presentation.theme.LocalColors

@Composable
fun BottomNavigationBar(navController: NavController) {
    val colors = LocalColors.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(colors.surface)
            .padding(horizontal = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemView(BottomNavItem.Feed, navController)
            BottomNavItemView(BottomNavItem.Cards, navController)
        }
    }
}

@Composable
fun BottomNavItemView(item: BottomNavItem, navController: NavController) {
    AsyncImage(
        model = item.icon,
        contentDescription = item.label,
        modifier = Modifier
            .size(32.dp)
            .clickable { navController.navigate(item.route) }
    )
}