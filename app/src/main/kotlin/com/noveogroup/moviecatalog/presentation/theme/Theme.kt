package com.noveogroup.moviecatalog.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
)

private val LightColorScheme = lightColors(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
)

private val ThemeShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colors = colorScheme,
        shapes = ThemeShapes,
        content = content
    )
}