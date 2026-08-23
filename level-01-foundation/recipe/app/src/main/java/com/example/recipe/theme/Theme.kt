package com.example.recipe.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Basil,
    onPrimary = OnBasil,
    primaryContainer = BasilContainer,
    onPrimaryContainer = OnBasilContainer,
    secondary = Saffron,
    onSecondary = OnSaffron,
    secondaryContainer = SaffronContainer,
    onSecondaryContainer = OnSaffronContainer,
    tertiary = Tomato,
    onTertiary = OnTomato,
    tertiaryContainer = TomatoContainer,
    onTertiaryContainer = OnTomatoContainer,
    background = WarmCanvas,
    onBackground = Ink,
    surface = WarmSurface,
    onSurface = Ink,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = MutedInk,
    outline = Outline,
    outlineVariant = OutlineVariant,
    error = Tomato,
    onError = OnTomato,
    errorContainer = TomatoContainer,
    onErrorContainer = OnTomatoContainer,
    inverseSurface = Ink,
    inverseOnSurface = WarmCanvas,
    inversePrimary = BasilLight,
    surfaceDim = Color(0xFFE5DDD3),
    surfaceBright = WarmSurface,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFBF5EC),
    surfaceContainer = Color(0xFFF7F0E7),
    surfaceContainerHigh = Color(0xFFF0E9DF),
    surfaceContainerHighest = Color(0xFFEAE2D8),
)

private val DarkColorScheme = darkColorScheme(
    primary = BasilLight,
    onPrimary = OnBasilLight,
    primaryContainer = BasilContainerDark,
    onPrimaryContainer = OnBasilContainerDark,
    secondary = SaffronLight,
    onSecondary = OnSaffronLight,
    secondaryContainer = SaffronContainerDark,
    onSecondaryContainer = OnSaffronContainerDark,
    tertiary = TomatoLight,
    onTertiary = OnTomatoLight,
    tertiaryContainer = TomatoContainerDark,
    onTertiaryContainer = OnTomatoContainerDark,
    background = NightCanvas,
    onBackground = NightInk,
    surface = NightSurface,
    onSurface = NightInk,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightMutedInk,
    outline = NightOutline,
    outlineVariant = NightOutlineVariant,
    error = TomatoLight,
    onError = OnTomatoLight,
    errorContainer = TomatoContainerDark,
    onErrorContainer = OnTomatoContainerDark,
    inverseSurface = NightInk,
    inverseOnSurface = NightCanvas,
    inversePrimary = Basil,
    surfaceDim = NightCanvas,
    surfaceBright = Color(0xFF3D3933),
    surfaceContainerLowest = Color(0xFF100E0B),
    surfaceContainerLow = Color(0xFF1B1814),
    surfaceContainer = Color(0xFF211D19),
    surfaceContainerHigh = Color(0xFF2B2722),
    surfaceContainerHighest = Color(0xFF36312B),
)

@Composable
fun RecipeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )
}
