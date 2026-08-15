package com.example.contactlist.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
  lightColorScheme(
    primary = ContactBlue,
    onPrimary = OnContactBlue,
    primaryContainer = ContactBlueContainer,
    onPrimaryContainer = OnContactBlueContainer,
    secondary = ContactBlue,
    onSecondary = OnContactBlue,
    secondaryContainer = ContactBlueContainer,
    onSecondaryContainer = OnContactBlueContainer,
    tertiary = ContactBlue,
    onTertiary = OnContactBlue,
    tertiaryContainer = ContactBlueContainer,
    onTertiaryContainer = OnContactBlueContainer,
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
    primary = ContactBlueLight,
    onPrimary = OnContactBlueLight,
    primaryContainer = ContactBlueContainerDark,
    onPrimaryContainer = OnContactBlueContainerDark,
    secondary = ContactBlueLight,
    onSecondary = OnContactBlueLight,
    secondaryContainer = ContactBlueContainerDark,
    onSecondaryContainer = OnContactBlueContainerDark,
    tertiary = ContactBlueLight,
    onTertiary = OnContactBlueLight,
    tertiaryContainer = ContactBlueContainerDark,
    onTertiaryContainer = OnContactBlueContainerDark,
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
