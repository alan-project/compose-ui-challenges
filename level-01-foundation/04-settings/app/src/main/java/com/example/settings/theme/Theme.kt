package com.example.settings.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
  lightColorScheme(
    primary = Forest,
    onPrimary = OnForest,
    primaryContainer = ForestContainer,
    onPrimaryContainer = OnForestContainer,
    secondary = EarthTeal,
    onSecondary = OnEarthTeal,
    secondaryContainer = SageContainer,
    onSecondaryContainer = OnSageContainer,
    tertiary = Amber,
    onTertiary = OnAmber,
    tertiaryContainer = WarmSand,
    onTertiaryContainer = OnWarmSand,
    background = SandCanvas,
    onBackground = EarthInk,
    surface = CreamSurface,
    onSurface = EarthInk,
    surfaceVariant = EarthSurfaceVariant,
    onSurfaceVariant = EarthMutedInk,
    outline = EarthOutline,
    outlineVariant = EarthOutlineVariant,
    error = SettingsError,
    onError = CreamSurface,
    errorContainer = SettingsErrorContainer,
    onErrorContainer = SettingsOnErrorContainer,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = ForestLight,
    onPrimary = OnForestLight,
    primaryContainer = DeepForest,
    onPrimaryContainer = OnDeepForest,
    secondary = TealLight,
    onSecondary = OnTealLight,
    secondaryContainer = DeepTeal,
    onSecondaryContainer = OnDeepTeal,
    tertiary = AmberLight,
    onTertiary = OnAmberLight,
    tertiaryContainer = DeepAmber,
    onTertiaryContainer = OnDeepAmber,
    background = EarthNight,
    onBackground = EarthNightText,
    surface = EarthNightSurface,
    onSurface = EarthNightText,
    surfaceVariant = EarthNightElevated,
    onSurfaceVariant = EarthNightMutedText,
    outline = EarthNightOutline,
    outlineVariant = EarthNightOutlineVariant,
    error = SettingsDarkError,
    onError = SettingsDarkOnError,
    errorContainer = SettingsDarkErrorContainer,
    onErrorContainer = SettingsDarkOnErrorContainer,
  )

@Composable
fun SettingsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
