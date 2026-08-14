package com.example.checkout.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
    lightColorScheme(
        primary = Violet,
        onPrimary = OnViolet,
        primaryContainer = Lavender,
        onPrimaryContainer = OnLavender,
        secondary = Cyan,
        onSecondary = OnCyan,
        secondaryContainer = IceCyan,
        onSecondaryContainer = OnIceCyan,
        tertiary = Golden,
        onTertiary = OnGolden,
        tertiaryContainer = Lemon,
        onTertiaryContainer = OnLemon,
        background = CoolCanvas,
        onBackground = Ink,
        surface = White,
        onSurface = Ink,
        surfaceVariant = CoolSurface,
        onSurfaceVariant = MutedInk,
        outline = Outline,
        outlineVariant = OutlineVariant,
        error = ColorTokens.Error,
        onError = White,
        errorContainer = ColorTokens.ErrorContainer,
        onErrorContainer = ColorTokens.OnErrorContainer,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = VioletLight,
        onPrimary = OnVioletLight,
        primaryContainer = DeepViolet,
        onPrimaryContainer = OnDeepViolet,
        secondary = CyanLight,
        onSecondary = OnCyanLight,
        secondaryContainer = DeepCyan,
        onSecondaryContainer = OnDeepCyan,
        tertiary = GoldenLight,
        onTertiary = OnGoldenLight,
        tertiaryContainer = DeepGolden,
        onTertiaryContainer = OnDeepGolden,
        background = Night,
        onBackground = OnNight,
        surface = NightSurface,
        onSurface = OnNight,
        surfaceVariant = NightElevated,
        onSurfaceVariant = OnNightVariant,
        outline = OutlineDark,
        outlineVariant = OutlineVariantDark,
        error = ColorTokens.DarkError,
        onError = ColorTokens.DarkOnError,
        errorContainer = ColorTokens.DarkErrorContainer,
        onErrorContainer = ColorTokens.DarkOnErrorContainer,
    )

private object ColorTokens {
    val Error = androidx.compose.ui.graphics.Color(0xFFBA1A1A)
    val ErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6)
    val OnErrorContainer = androidx.compose.ui.graphics.Color(0xFF410002)
    val DarkError = androidx.compose.ui.graphics.Color(0xFFFFB4AB)
    val DarkOnError = androidx.compose.ui.graphics.Color(0xFF690005)
    val DarkErrorContainer = androidx.compose.ui.graphics.Color(0xFF93000A)
    val DarkOnErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6)
}

@Composable
fun CheckoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = CheckoutTypography,
        content = content,
    )
}
