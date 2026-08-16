package com.example.settings.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = OnOrange,
    primaryContainer = OrangeContainer,
    onPrimaryContainer = OnOrangeContainer,
    secondary = Cinnamon,
    onSecondary = OnCinnamon,
    secondaryContainer = CinnamonContainer,
    onSecondaryContainer = OnCinnamonContainer,
    tertiary = WarmGold,
    onTertiary = OnWarmGold,
    tertiaryContainer = WarmGoldContainer,
    onTertiaryContainer = OnWarmGoldContainer,
    background = WarmCanvas,
    onBackground = WarmInk,
    surface = WarmSurface,
    onSurface = WarmInk,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = WarmMutedInk,
    outline = WarmOutline,
    outlineVariant = WarmOutlineVariant,
    error = SettingsError,
    onError = WarmSurface,
    errorContainer = SettingsErrorContainer,
    onErrorContainer = SettingsOnErrorContainer,
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangeLight,
    onPrimary = OnOrangeLight,
    primaryContainer = DeepOrange,
    onPrimaryContainer = OnDeepOrange,
    secondary = CinnamonLight,
    onSecondary = OnCinnamonLight,
    secondaryContainer = DeepCinnamon,
    onSecondaryContainer = OnDeepCinnamon,
    tertiary = WarmGoldLight,
    onTertiary = OnWarmGoldLight,
    tertiaryContainer = DeepGold,
    onTertiaryContainer = OnDeepGold,
    background = CoffeeNight,
    onBackground = CoffeeText,
    surface = CoffeeSurface,
    onSurface = CoffeeText,
    surfaceVariant = CoffeeElevated,
    onSurfaceVariant = CoffeeMutedText,
    outline = CoffeeOutline,
    outlineVariant = CoffeeOutlineVariant,
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
