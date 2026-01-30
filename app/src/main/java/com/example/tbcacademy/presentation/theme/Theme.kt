package com.example.tbcacademy.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ThemeColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val error: Color,
    val darkerBackground: Color,
    val onSurface: Color,
)

val LightColors = ThemeColors(
    primary = Blue,
    background = DarkBlue,
    surface = DarkerBlue,
    textPrimary = White,
    textSecondary = Gray,
    error = Red,
    darkerBackground = DarkGreen,
    onSurface = White,
)

val LocalColors = staticCompositionLocalOf { LightColors }

@Composable
fun ComposeAppTheme(
    colors: ThemeColors = LightColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}
