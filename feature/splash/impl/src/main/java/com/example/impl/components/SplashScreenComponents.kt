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

    val titleAlpha by animateFloatAsState(
        targetValue = if (titleVisible) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "titleAlpha"
    )
    val titleOffset by animateFloatAsState(
        targetValue = if (titleVisible) 0f else -40f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "titleOffset"
    )

    val subtitleAlpha by animateFloatAsState(
        targetValue = if (subtitleVisible) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "subtitleAlpha"
    )
    val subtitleOffset by animateFloatAsState(
        targetValue = if (subtitleVisible) 0f else 40f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "subtitleOffset"
    )

    Text(
        "My Fitness",
        fontSize = 44.sp,
        color = White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.graphicsLayer {
            alpha = titleAlpha
            translationY = titleOffset
        }
    )

    Text(
        "Journey",
        fontSize = 44.sp,
        color = White,
        fontWeight = FontWeight.Thin,
        modifier = Modifier.graphicsLayer {
            alpha = subtitleAlpha
            translationY = subtitleOffset
        }
    )
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

    val alpha by animateFloatAsState(
        targetValue = if (subtitleVisible) 1f else 0f,
        animationSpec = tween(800, easing = LinearOutSlowInEasing),
        label = "subtitleAlpha"
    )
    val offset by animateFloatAsState(
        targetValue = if (subtitleVisible) 0f else 30f,
        animationSpec = tween(800, easing = LinearOutSlowInEasing),
        label = "subtitleOffset"
    )

    Text(
        text = "Fuel Your Body, Build Your Strength",
        fontSize = 22.sp,
        color = White,
        fontWeight = FontWeight.W200,
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha
            translationY = offset
        }
    )
}