package com.example.composemarket.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
  lightColorScheme(
    primary = GridRoyal,
    onPrimary = OnGridRoyal,
    primaryContainer = GridRoyalContainer,
    onPrimaryContainer = OnGridRoyalContainer,
    secondary = GridEmerald,
    onSecondary = OnGridEmerald,
    secondaryContainer = GridEmeraldContainer,
    onSecondaryContainer = OnGridEmeraldContainer,
    tertiary = GridOrange,
    onTertiary = OnGridOrange,
    tertiaryContainer = GridOrangeContainer,
    onTertiaryContainer = OnGridOrangeContainer,
    background = GridCanvas,
    onBackground = GridInk,
    surface = GridSurface,
    onSurface = GridInk,
    surfaceVariant = GridSurfaceVariant,
    onSurfaceVariant = GridMutedInk,
    outline = GridOutline,
    outlineVariant = GridOutlineVariant,
    inverseSurface = GridInk,
    inverseOnSurface = GridCanvas,
    inversePrimary = GridRoyalDark,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = GridRoyalDark,
    onPrimary = OnGridRoyalDark,
    primaryContainer = GridRoyalContainerDark,
    onPrimaryContainer = OnGridRoyalContainerDark,
    secondary = GridEmeraldDark,
    onSecondary = OnGridEmeraldDark,
    secondaryContainer = GridEmeraldContainerDark,
    onSecondaryContainer = OnGridEmeraldContainerDark,
    tertiary = GridOrangeDark,
    onTertiary = OnGridOrangeDark,
    tertiaryContainer = GridOrangeContainerDark,
    onTertiaryContainer = OnGridOrangeContainerDark,
    background = GridCanvasDark,
    onBackground = GridOnSurfaceDark,
    surface = GridSurfaceDark,
    onSurface = GridOnSurfaceDark,
    surfaceVariant = GridSurfaceVariantDark,
    onSurfaceVariant = GridOnSurfaceVariantDark,
    outline = GridOutlineDark,
    outlineVariant = GridOutlineVariantDark,
    inverseSurface = GridOnSurfaceDark,
    inverseOnSurface = GridCanvasDark,
    inversePrimary = GridRoyal,
  )

@Composable
fun ComposeMarketTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
