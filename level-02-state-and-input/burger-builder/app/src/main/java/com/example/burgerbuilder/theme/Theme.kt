package com.example.burgerbuilder.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
  lightColorScheme(
    primary = Bun,
    onPrimary = OnBun,
    primaryContainer = BunContainer,
    onPrimaryContainer = OnBunContainer,
    secondary = Pickle,
    onSecondary = OnPickle,
    secondaryContainer = PickleContainer,
    onSecondaryContainer = OnPickleContainer,
    tertiary = Cheddar,
    onTertiary = OnCheddar,
    tertiaryContainer = CheddarContainer,
    onTertiaryContainer = OnCheddarContainer,
    background = WarmCanvas,
    onBackground = Ink,
    surface = WarmSurface,
    onSurface = Ink,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = MutedInk,
    outline = Outline,
    outlineVariant = OutlineVariant,
    inverseSurface = Ink,
    inverseOnSurface = WarmCanvas,
    inversePrimary = BunLight,
    surfaceDim = Color(0xFFE8D6CF),
    surfaceBright = WarmSurface,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFFF1EC),
    surfaceContainer = Color(0xFFFAEBE5),
    surfaceContainerHigh = Color(0xFFF4E5DF),
    surfaceContainerHighest = Color(0xFFEEDED8),
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = BunLight,
    onPrimary = OnBunLight,
    primaryContainer = BunContainerDark,
    onPrimaryContainer = OnBunContainerDark,
    secondary = PickleLight,
    onSecondary = OnPickleLight,
    secondaryContainer = PickleContainerDark,
    onSecondaryContainer = OnPickleContainerDark,
    tertiary = CheddarLight,
    onTertiary = OnCheddarLight,
    tertiaryContainer = CheddarContainerDark,
    onTertiaryContainer = OnCheddarContainerDark,
    background = NightCanvas,
    onBackground = NightInk,
    surface = NightSurface,
    onSurface = NightInk,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightMutedInk,
    outline = NightOutline,
    outlineVariant = NightOutlineVariant,
    inverseSurface = NightInk,
    inverseOnSurface = NightCanvas,
    inversePrimary = Bun,
    surfaceDim = NightCanvas,
    surfaceBright = Color(0xFF493936),
    surfaceContainerLowest = Color(0xFF140C09),
    surfaceContainerLow = Color(0xFF231916),
    surfaceContainer = Color(0xFF281D1A),
    surfaceContainerHigh = Color(0xFF332825),
    surfaceContainerHighest = Color(0xFF3F332F),
  )

@Composable
fun BurgerBuilderTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
