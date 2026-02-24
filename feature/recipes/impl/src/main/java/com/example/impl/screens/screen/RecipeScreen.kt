package com.example.impl.screens.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.PrimaryColorPink
import com.example.designsystem.theme.White
import com.example.domain.model.recipe.Recipe
import com.example.domain.model.recipe.RecipeCategory
import com.example.impl.screens.vm.RecipeViewModel
import com.example.ui.base.BaseScreen

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel()
) {
    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onSideEffect = {}
    ) { state, _ ->

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryColorPink)
            }
            return@BaseScreen
        }

        if (state.error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error, color = Color.Red)
            }
            return@BaseScreen
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 25.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recipes",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            state.categories.forEach { category ->
                RecipeCategorySection(category = category)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun RecipeCategorySection(category: RecipeCategory) {
    Column {
        Text(
            text = category.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 22.dp, top = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.description,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            color = LightGray,
            modifier = Modifier.padding(start = 22.dp, end = 80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(start = 22.dp, end = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.recipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    modifier = Modifier.width(280.dp)
                )
            }
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier.height(260.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Prep ${recipe.prepTime}",
                        color = LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = "  |  ", color = LightGray, fontSize = 12.sp)
                    Text(
                        text = "Cook ${recipe.cookTime}",
                        color = LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = recipe.title,
                    color = Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = recipe.description,
                    color = LightGray,
                    fontSize = 12.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MacroChip(label = "${recipe.calories} kcal", color = PrimaryColorPink)
                    MacroChip(label = "${recipe.protein.toInt()}g protein", color = Color(0xFF4CAF50))
                    MacroChip(label = "${recipe.carbs.toInt()}g carbs", color = Color(0xFFFF9800))
                }
            }
        }
    }
}

@Composable
private fun MacroChip(label: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}