package com.example.impl.screens.add_food.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.R
import com.example.designsystem.theme.MilkyPink
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.VeryLightGray
import com.example.designsystem.theme.White
import com.example.domain.model.food.Food
import com.example.impl.screens.add_food.contract.AddFoodEvent
import com.example.impl.screens.add_food.vm.AddFoodViewModel
import com.example.model.MealType
import com.example.ui.base.BaseScreen

@Composable
fun AddFoodScreen(
    viewModel: AddFoodViewModel = hiltViewModel(),
    mealType: MealType,
    onFoodSelected: (Food) -> Unit
) {
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel
    ) { state, onEvent ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            SearchField(
                query = state.query,
                onQueryChange = { onEvent(AddFoodEvent.Search(it)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.padding(start = 14.dp),
                text = "Suggested",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn {
                items(state.foodList) { food ->
                    SearchFoodItem(
                        food = food,
                        onFoodSelected = onFoodSelected
                    )
                }
            }
        }
    }
}

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
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
fun SearchFoodItem(
    food: Food,
    onFoodSelected: (Food) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
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
                Text(text = food.name)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "${food.calories} cals · Carbs ${food.carbs}g · Protein ${food.protein}g")
            }

            Spacer(modifier = Modifier.weight(1f))

            CircleItem(onClick = { onFoodSelected(food) })
        }
    }
}

@Composable
fun CircleItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color = MilkyPink)
            .clickable { onClick() },
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
    AddFoodScreen(
        mealType = MealType.BREAKFAST,
        onFoodSelected = {}
    )
}