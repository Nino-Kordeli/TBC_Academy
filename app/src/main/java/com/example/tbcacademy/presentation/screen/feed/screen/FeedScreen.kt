package com.example.tbcacademy.presentation.screen.feed.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.tbcacademy.domain.model.Post
import com.example.tbcacademy.domain.model.Story
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.common.DateFormatter
import com.example.tbcacademy.presentation.screen.feed.contract.FeedEvent
import com.example.tbcacademy.presentation.screen.feed.contract.FeedSideEffect
import com.example.tbcacademy.presentation.screen.feed.vm.FeedViewModel
import com.example.tbcacademy.presentation.theme.LocalColors

@Composable
fun FeedScreen(
    navigator: NavController,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val colors = LocalColors.current
    val context = navigator.context

    BaseScreen(
        viewModel = viewModel,
        onSideEffect = { effect ->
            if (effect is FeedSideEffect.ShowError) {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    ) { state, onEvent ->

        LaunchedEffect(Unit) {
            onEvent(FeedEvent.LoadData)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
            Column {
                LazyRow(
                    contentPadding = PaddingValues(start = 28.dp, top = 24.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.stories) { story ->
                        StoryItem(story)
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 29.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(state.posts) { post ->
                        PostItem(post)
                    }
                }
            }
        }
    }
}

@Composable
fun StoryItem(story: Story) {
    val colors = LocalColors.current

    Box(
        modifier = Modifier
            .size(140.dp, 200.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(colors.darkerBackground)
    ) {
        AsyncImage(
            model = story.cover,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = story.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 26.dp)
        )
    }
}

@Composable
fun PostItem(post: Post) {
    val colors = LocalColors.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(colors.surface)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = post.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(10.dp))

            Column {
                Text(
                    "${post.firstName} ${post.lastName}",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textPrimary
                )

                Text(
                    DateFormatter.formatPostDate(post.postDate),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
        }

        if (!post.postDesc.isNullOrEmpty()) {
            Spacer(Modifier.height(14.dp))
            Text(
                post.postDesc,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
        }

        Spacer(Modifier.height(8.dp))
        PostImages(post.images)
        Spacer(Modifier.height(16.dp))
        DividerLine()
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ActionItem("💬", "24 Comments")
            Spacer(Modifier.width(24.dp))
            ActionItem("❤️", "128 Likes")
            Spacer(Modifier.width(24.dp))
            ActionItem("🔗", "Share")
        }

        Spacer(Modifier.height(21.dp))
        DividerLine()
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = post.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.darkerBackground)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Write comment...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
        }
    }
}

@Composable
fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.8.dp)
            .background(LocalColors.current.textSecondary)
    )
}

@Composable
fun ActionItem(icon: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(icon)
        Spacer(Modifier.width(9.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalColors.current.textSecondary
        )
    }
}

@Composable
fun PostImages(images: List<String>) {
    when (images.size) {
        1 -> AsyncImage(
            model = images[0],
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        2 -> Row {
            images.forEach {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }

        else -> Row {
            AsyncImage(
                model = images[0],
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(180.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(modifier = Modifier.weight(1f)) {
                images.drop(1).take(2).forEach {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}
