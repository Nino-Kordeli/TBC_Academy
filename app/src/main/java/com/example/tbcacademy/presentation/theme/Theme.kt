package com.example.tbcacademy.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray

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

val DarkColors = ThemeColors(
    primary = DarkBlue,
    background = Black,
    surface = DarkerBlue,
    textPrimary = White,
    textSecondary = Gray,
    error = Red,
    darkerBackground = DarkGray,
    onSurface = White,
)

val LocalColors = staticCompositionLocalOf { LightColors }

@Composable
fun ComposeAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}