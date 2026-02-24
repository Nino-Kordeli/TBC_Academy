package com.example.impl.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.White
import kotlinx.coroutines.delay

@Composable
internal fun AnimatedDumbbell(visible: Boolean) {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val rotation by animateFloatAsState(
        targetValue = if (visible) 0f else -180f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "rotation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            easing = LinearOutSlowInEasing
        ),
        label = "alpha"
    )

    Image(
        painter = painterResource(com.example.designsystem.R.drawable.dumbbell),
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .scale(scale)
            .rotate(rotation)
            .alpha(alpha)
    )
}

@Composable
internal fun AnimatedTitle(visible: Boolean) {
    var titleVisible by remember { mutableStateOf(false) }
    var subtitleVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(300)
            titleVisible = true
            delay(200)
            subtitleVisible = true
        }
    }

    AnimatedVisibility(
        visible = titleVisible,
        enter = slideInVertically(
            initialOffsetY = { -40 },
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(600))
    ) {
        Text(
            "My Fitness",
            fontSize = 44.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
    }

    AnimatedVisibility(
        visible = subtitleVisible,
        enter = slideInVertically(
            initialOffsetY = { 40 },
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(600))
    ) {
        Text(
            "Journey",
            fontSize = 44.sp,
            color = White,
            fontWeight = FontWeight.Thin
        )
    }
}

@Composable
internal fun AnimatedDivider(visible: Boolean) {
    var dividerVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(800)
            dividerVisible = true
        }
    }

    val width by animateFloatAsState(
        targetValue = if (dividerVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "width"
    )

    HorizontalDivider(
        color = White,
        modifier = Modifier
            .padding(horizontal = 150.dp)
            .graphicsLayer {
                scaleX = width
            }
    )
}

@Composable
internal fun AnimatedSubtitle(visible: Boolean) {
    var subtitleVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(1200)
            subtitleVisible = true
        }
    }

    AnimatedVisibility(
        visible = subtitleVisible,
        enter = slideInVertically(
            initialOffsetY = { 30 },
            animationSpec = tween(800, easing = LinearOutSlowInEasing)
        ) + fadeIn(
            animationSpec = tween(800)
        )
    ) {
        Text(
            text = "Fuel Your Body, Build Your Strength",
            fontSize = 22.sp,
            color = White,
            fontWeight = FontWeight.W200,
        )
    }
}