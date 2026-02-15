package com.example.tbcacademy.presentation.screens.add_food.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcacademy.presentation.theme.MilkyPink
import com.example.tbcacademy.presentation.theme.PrimaryBlue
import com.example.tbcacademy.presentation.theme.VeryLightGray
import com.example.tbcacademy.presentation.theme.White
import com.example.tbcacademy.R

@Composable
fun AddFoodScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        SearchField()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(start = 14.dp),
            text = "Suggested",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        SearchFoodItem()

    }
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search for food") },
        shape = RoundedCornerShape(35.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = PrimaryBlue
        )
    )
}

@Composable
fun SearchFoodItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = VeryLightGray)
                .padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Toast bread")

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "192 cals, Toast bread, 1 slice")
            }

            Spacer(modifier = Modifier.weight(1f))

            CircleItem()

            LazyColumn() { }//TODO: aq minda mokled fooditems romlebic wamova apidan, is zeda row
        }
    }
}

@Composable
fun CircleItem() {

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color = MilkyPink),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
@Preview
fun AddFoodStepPreview() {
    AddFoodScreen()
}