package com.example.financedashboard.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
  lightColorScheme(
    primary = DashboardNavy,
    onPrimary = OnDashboardNavy,
    primaryContainer = NavyContainer,
    onPrimaryContainer = OnNavyContainer,
    secondary = ElectricBlue,
    onSecondary = OnElectricBlue,
    secondaryContainer = BlueContainer,
    onSecondaryContainer = OnBlueContainer,
    tertiary = DataMint,
    onTertiary = OnDataMint,
    tertiaryContainer = MintContainer,
    onTertiaryContainer = OnMintContainer,
    background = CoolCanvas,
    onBackground = DashboardInk,
    surface = White,
    onSurface = DashboardInk,
    surfaceVariant = CoolSurfaceVariant,
    onSurfaceVariant = DashboardMutedInk,
    outline = DashboardOutline,
    outlineVariant = DashboardOutlineVariant,
    error = DataCoral,
    onError = OnDataCoral,
    errorContainer = CoralContainer,
    onErrorContainer = OnCoralContainer,
    inverseSurface = DashboardInk,
    inverseOnSurface = CoolCanvas,
    inversePrimary = NavyLight,
    surfaceDim = Color(0xFFDCE2EA),
    surfaceBright = White,
    surfaceContainerLowest = White,
    surfaceContainerLow = Color(0xFFF0F3F8),
    surfaceContainer = Color(0xFFEBEFF5),
    surfaceContainerHigh = Color(0xFFE5EAF1),
    surfaceContainerHighest = Color(0xFFDEE4EC),
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = NavyLight,
    onPrimary = OnNavyLight,
    primaryContainer = NavyContainerDark,
    onPrimaryContainer = OnNavyContainerDark,
    secondary = ElectricBlueLight,
    onSecondary = OnElectricBlueLight,
    secondaryContainer = BlueContainerDark,
    onSecondaryContainer = OnBlueContainerDark,
    tertiary = DataMintLight,
    onTertiary = OnDataMintLight,
    tertiaryContainer = MintContainerDark,
    onTertiaryContainer = OnMintContainerDark,
    background = NightCanvas,
    onBackground = NightInk,
    surface = NightSurface,
    onSurface = NightInk,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightMutedInk,
    outline = NightOutline,
    outlineVariant = NightOutlineVariant,
    error = DataCoralLight,
    onError = OnDataCoralLight,
    errorContainer = CoralContainerDark,
    onErrorContainer = OnCoralContainerDark,
    inverseSurface = NightInk,
    inverseOnSurface = NightCanvas,
    inversePrimary = DashboardNavy,
    surfaceDim = NightCanvas,
    surfaceBright = Color(0xFF313A47),
    surfaceContainerLowest = Color(0xFF070C13),
    surfaceContainerLow = Color(0xFF101722),
    surfaceContainer = Color(0xFF161E2A),
    surfaceContainerHigh = Color(0xFF202936),
    surfaceContainerHighest = Color(0xFF2B3542),
  )

@Composable
fun FinanceDashboardTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
