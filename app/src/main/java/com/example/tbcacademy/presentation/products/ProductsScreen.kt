package com.example.tbcacademy.presentation.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.composeapp.ui.theme.BluishGray
import com.example.composeapp.ui.theme.BrightGreen
import com.example.composeapp.ui.theme.DarkBlue
import com.example.composeapp.ui.theme.LightBlue
import com.example.composeapp.ui.theme.White
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.model.CategoryUi
import com.example.tbcacademy.presentation.model.ProductUi
import com.example.tbcacademy.presentation.products.contract.ProductsEvent
import com.example.tbcacademy.presentation.products.vm.ProductsViewModel

@Composable
fun ProductsScreen(
    navigator: NavController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = {}
    ) { state, onEvent ->
        val filteredProducts = if (state.selectedCategory == "all") {
            state.products
        } else {
            state.products.filter { it.category == state.selectedCategory }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {
            CategoryRow(
                categories = state.categories,
                selected = state.selectedCategory,
                onClick = { onEvent(ProductsEvent.CategorySelected(it)) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProductGrid(products = filteredProducts)
        }
    }
}

@Composable
fun CategoryRow(
    categories: List<CategoryUi>,
    selected: String,
    onClick: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                isSelected = category.category == selected,
                onClick = { onClick(category.category) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: CategoryUi,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) BrightGreen else LightBlue)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Text(
            text = category.category.replaceFirstChar { it.uppercase() },
            color = if (isSelected) White else BluishGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ProductGrid(products: List<ProductUi>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(23.dp)) {
        items(products.chunked(2)) { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(19.dp)
            ) {
                rowProducts.forEach { product ->
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = product.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            color = BluishGray
                        )

                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (rowProducts.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
fun ProductsScreenPreview() {
    ProductsScreen(navigator = rememberNavController())
}
