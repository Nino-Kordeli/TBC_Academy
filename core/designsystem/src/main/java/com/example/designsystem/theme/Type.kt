package com.example.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight

// Set of Material typography styles to start with
val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        displayMedium = displayMedium.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        displaySmall = displaySmall.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),

        headlineLarge = headlineLarge.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        headlineMedium = headlineMedium.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        headlineSmall = headlineSmall.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),

        titleLarge = titleLarge.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        titleMedium = titleMedium.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        titleSmall = titleSmall.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),

        bodyLarge = bodyLarge.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        bodyMedium = bodyMedium.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        bodySmall = bodySmall.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),

        labelLarge = labelLarge.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        labelMedium = labelMedium.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
        labelSmall = labelSmall.copy(fontFamily = AppFontFamily, fontWeight = FontWeight.Light),
    )
}
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
