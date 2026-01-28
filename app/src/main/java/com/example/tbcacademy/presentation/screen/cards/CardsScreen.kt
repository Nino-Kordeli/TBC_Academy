package com.example.tbcacademy.presentation.screen.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeapp.ui.theme.LightGreen
import com.example.tbcacademy.R
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.presentation.screen.contract.CardsEvent
import com.example.tbcacademy.presentation.screen.contract.CardsState
import com.example.tbcacademy.presentation.theme.ComposeAppTheme
import com.example.tbcacademy.presentation.theme.Spacing

@Composable
fun CardsScreen(
    state: CardsState,
    onEvent: (CardsEvent) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(Unit) { onEvent(CardsEvent.LoadCards) }

    ComposeAppTheme(darkTheme = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 78.dp, end = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onToggleTheme
                ) {
                    Text(if (isDarkTheme) "Light Mode" else "Dark Mode")
                }
            }

            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(horizontal = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                itemsIndexed(state.cards) { index, card ->
                    val scale by animateFloatAsState(
                        targetValue = if (isItemFocused(listState, index)) 1.05f else 0.9f,
                        label = ""
                    )
                    CardItem(card, modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    })
                }
            }

            BottomNavVisual(modifier = Modifier.align(Alignment.BottomCenter))

        }
    }
}

@Composable
fun CardItem(card: Cards, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(305.dp)
            .height(540.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box {
            AsyncImage(
                model = card.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = Spacing.l,
                        end = Spacing.l,
                        top = Spacing.l,
                        bottom = Spacing.xxl
                    )
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.location,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(Spacing.m))

                    Text(
                        text = "${card.altitudeM} m",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column {
                    Text(
                        text = card.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(Modifier.height(Spacing.m))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${card.stars * 20}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        StarRating(stars = card.stars)
                    }
                }
            }
        }
    }
}

@Composable
fun StarRating(stars: Int, maxStars: Int = 5) {
    Row {
        for (i in 1..maxStars) {
            Icon(
                painter = painterResource(if (i <= stars) R.drawable.ic_star else R.drawable.ic_star_outline),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun BottomNavVisual(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(LightGreen),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavIcon(R.drawable.ic_heart)
        BottomNavIcon(R.drawable.ic_home)
        BottomNavIcon(R.drawable.ic_messages)
    }
}

@Composable
fun BottomNavIcon(iconRes: Int) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}

fun isItemFocused(listState: LazyListState, index: Int): Boolean {
    val center = listState.layoutInfo.viewportStartOffset +
            listState.layoutInfo.viewportEndOffset / 2
    val itemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    val itemCenter = itemInfo?.let { it.offset + it.size / 2 } ?: return false
    return kotlin.math.abs(itemCenter - center) < itemInfo.size / 2
}

@Preview
@Composable
fun CardsScreenPreview() {
    var isDarkTheme = false
    val fakeState = CardsState(
        isLoading = false,
        cards = listOf(
            Cards(
                location = "Tbilisi, Georgia",
                altitudeM = 380,
                title = "Old Town Rooftop Workspace",
                image = "",
                stars = 4,
                id = 2
            ),
            Cards(
                location = "Gudauri, Georgia",
                altitudeM = 2200,
                title = "Mountain View Construction Site",
                image = "",
                stars = 5,
                id = 1
            )
        )
    )

    CardsScreen(
        state = fakeState,
        onEvent = {},
        isDarkTheme = isDarkTheme,
        onToggleTheme = { isDarkTheme = !isDarkTheme }
    )
}
