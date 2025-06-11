package com.example.foodorderingapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

// Create CompositionLocal for theme access throughout the app
val LocalAppTheme = compositionLocalOf { ThemeManager.getCurrentTheme() }

@Composable
fun FoodOrderingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val currentTheme = ThemeManager.getCurrentTheme()

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = currentTheme.primaryColor,
            background = currentTheme.backgroundColor.copy(alpha = 0.2f),
            surface = currentTheme.cardColor.copy(alpha = 0.3f)
        )
    } else {
        lightColorScheme(
            primary = currentTheme.primaryColor,
            background = currentTheme.backgroundColor,
            surface = currentTheme.cardColor
        )
    }

    CompositionLocalProvider(
        LocalAppTheme provides currentTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}