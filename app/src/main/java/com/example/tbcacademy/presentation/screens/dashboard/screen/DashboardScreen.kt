package com.example.tbcacademy.presentation.screens.dashboard.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.screens.dashboard.vm.DashboardViewModel
import com.example.tbcacademy.presentation.theme.Pink40
import com.example.tbcacademy.presentation.theme.White

@Composable
fun DashboardScreen(
    navigator: NavController,
    //  viewModel: DashboardViewModel = hiltViewModel()
) {
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

        )
        {
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
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Text(
                text = "Today",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp)
            )

            LazyRow {

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
fun Title() {
    Text(
        text = "My Fitness Journey",
        fontSize = 25.sp,
        color = Pink40,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(15.dp),
    )
}

@Composable
fun ProfileIcon(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_launcher_background)

    Image(
        painter = image,
        contentDescription = "",
        modifier = modifier
    )
}
