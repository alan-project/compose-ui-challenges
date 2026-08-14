package com.example.contactlist.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
  lightColorScheme(
    primary = Cerulean,
    onPrimary = OnCerulean,
    primaryContainer = AquaMist,
    onPrimaryContainer = OnAquaMist,
    secondary = Tangerine,
    onSecondary = OnTangerine,
    secondaryContainer = TangerineMist,
    onSecondaryContainer = OnTangerineMist,
    tertiary = GoldenOchre,
    onTertiary = OnGoldenOchre,
    tertiaryContainer = Sunflower,
    onTertiaryContainer = OnSunflower,
    background = ContactCanvas,
    onBackground = ContactInk,
    surface = ContactSurface,
    onSurface = ContactInk,
    surfaceVariant = ContactSurfaceVariant,
    onSurfaceVariant = ContactMutedInk,
    outline = ContactOutline,
    outlineVariant = ContactOutlineVariant,
    error = ContactError,
    onError = ContactSurface,
    errorContainer = ContactErrorContainer,
    onErrorContainer = ContactOnErrorContainer,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = AquaLight,
    onPrimary = OnAquaLight,
    primaryContainer = DeepAqua,
    onPrimaryContainer = OnDeepAqua,
    secondary = TangerineLight,
    onSecondary = OnTangerineLight,
    secondaryContainer = DeepTangerine,
    onSecondaryContainer = OnDeepTangerine,
    tertiary = SunflowerLight,
    onTertiary = OnSunflowerLight,
    tertiaryContainer = DeepSunflower,
    onTertiaryContainer = OnDeepSunflower,
    background = ContactNight,
    onBackground = ContactNightText,
    surface = ContactNightSurface,
    onSurface = ContactNightText,
    surfaceVariant = ContactNightElevated,
    onSurfaceVariant = ContactNightMutedText,
    outline = ContactNightOutline,
    outlineVariant = ContactNightOutlineVariant,
    error = ContactDarkError,
    onError = ContactDarkOnError,
    errorContainer = ContactDarkErrorContainer,
    onErrorContainer = ContactDarkOnErrorContainer,
  )

@Composable
fun ContactListTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
