package com.example.ui.components.bottom_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
    hasSearch: Boolean,
    navigator: BottomBarNavigator
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val barHeight = 80.dp + bottomPadding
    val totalHeight = if (hasSearch) 160.dp + bottomPadding else barHeight

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(totalHeight)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.vector_4),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(totalHeight)
                .align(Alignment.BottomCenter),
            colorFilter = ColorFilter.tint(PrimaryBlue)
        )

        if (hasSearch) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(55.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 18.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .clickable { navigator.onNavigate(BottomBarDestination.Search) },
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(barHeight)
                .padding(bottom = bottomPadding), // push above nav bar
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
                iconRes = R.drawable.ic_more,
                label = "More",
                selected = currentDestination is BottomBarDestination.More,
                onClick = { navigator.onNavigate(BottomBarDestination.More) },
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
