package com.example.burgerbuilder.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
    lightColorScheme(
        primary = DriveThruRed,
        onPrimary = OnDriveThruRed,
        primaryContainer = DriveThruRedContainer,
        onPrimaryContainer = OnDriveThruRedContainer,
        secondary = GoldenMustard,
        onSecondary = OnGoldenMustard,
        secondaryContainer = GoldenYellowContainer,
        onSecondaryContainer = OnGoldenYellowContainer,
        tertiary = FreshGreen,
        onTertiary = OnFreshGreen,
        tertiaryContainer = FreshGreenContainer,
        onTertiaryContainer = OnFreshGreenContainer,
        background = FryPaper,
        onBackground = Charcoal,
        surface = CounterWhite,
        onSurface = Charcoal,
        surfaceVariant = WarmSurfaceVariant,
        onSurfaceVariant = MutedCharcoal,
        outline = Outline,
        outlineVariant = OutlineVariant,
        inverseSurface = Charcoal,
        inverseOnSurface = FryPaper,
        inversePrimary = DriveThruRedLight,
        surfaceDim = Color(0xFFDEDEDE),
        surfaceBright = CounterWhite,
        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color(0xFFFAFAFA),
        surfaceContainer = Color(0xFFF5F5F5),
        surfaceContainerHigh = Color(0xFFEFEFEF),
        surfaceContainerHighest = Color(0xFFE7E7E7),
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = DriveThruRedLight,
        onPrimary = OnDriveThruRedLight,
        primaryContainer = DriveThruRedContainerDark,
        onPrimaryContainer = OnDriveThruRedContainerDark,
        secondary = GoldenMustardLight,
        onSecondary = OnGoldenMustardLight,
        secondaryContainer = GoldenYellowContainerDark,
        onSecondaryContainer = OnGoldenYellowContainerDark,
        tertiary = FreshGreenLight,
        onTertiary = OnFreshGreenLight,
        tertiaryContainer = FreshGreenContainerDark,
        onTertiaryContainer = OnFreshGreenContainerDark,
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
        inversePrimary = DriveThruRed,
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
