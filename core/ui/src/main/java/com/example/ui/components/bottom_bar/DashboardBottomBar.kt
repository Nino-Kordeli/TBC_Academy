package com.example.ui.components.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White

@Composable
fun BottomBar(
    currentDestination: BottomBarDestination?,
    navigator: BottomBarNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(PrimaryBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                iconRes = R.drawable.ic_dashboard,
                label = "Dashboard",
                selected = currentDestination is BottomBarDestination.Home,
                onClick = { navigator.onNavigate(BottomBarDestination.Home) },
                modifier = Modifier.weight(1f)
            )
            NavigationItem(
                iconRes = R.drawable.ic_diary,
                label = "Diary",
                selected = currentDestination is BottomBarDestination.Diary,
                onClick = { navigator.onNavigate(BottomBarDestination.Diary) },
                modifier = Modifier.weight(1f)
            )
            NavigationItem(
                iconRes = R.drawable.ic_user_icon,
                label = "Profile",
                selected = currentDestination is BottomBarDestination.Profile,
                onClick = { navigator.onNavigate(BottomBarDestination.Profile) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavigationItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            modifier = Modifier.size(26.dp),
            tint = if (selected) White else White.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) White else White.copy(alpha = 0.7f)
        )
    }
}
