package com.example.checkout.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
    lightColorScheme(
        primary = Terracotta,
        onPrimary = White,
        primaryContainer = Apricot,
        onPrimaryContainer = TerracottaDark,
        secondary = Forest,
        onSecondary = White,
        secondaryContainer = ForestSoft,
        onSecondaryContainer = ColorTokens.DarkForest,
        background = Cream,
        onBackground = Ink,
        surface = White,
        onSurface = Ink,
        surfaceVariant = Sand,
        onSurfaceVariant = Cocoa,
        outline = ColorTokens.Outline,
        outlineVariant = Linen,
        error = ColorTokens.Error,
        onError = White,
        errorContainer = ColorTokens.ErrorContainer,
        onErrorContainer = ColorTokens.OnErrorContainer,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = TerracottaLight,
        onPrimary = ColorTokens.OnDarkPrimary,
        primaryContainer = DeepTerracotta,
        onPrimaryContainer = PaleCream,
        secondary = SageLight,
        onSecondary = ColorTokens.DarkForest,
        secondaryContainer = Forest,
        onSecondaryContainer = ColorTokens.OnDarkSecondaryContainer,
        background = Night,
        onBackground = PaleCream,
        surface = NightSurface,
        onSurface = PaleCream,
        surfaceVariant = NightElevated,
        onSurfaceVariant = ColorTokens.OnDarkSurfaceVariant,
        outline = ColorTokens.DarkOutline,
        outlineVariant = ColorTokens.DarkOutlineVariant,
        error = ColorTokens.DarkError,
        onError = ColorTokens.DarkOnError,
        errorContainer = ColorTokens.DarkErrorContainer,
        onErrorContainer = ColorTokens.DarkOnErrorContainer,
    )

private object ColorTokens {
    val DarkForest = androidx.compose.ui.graphics.Color(0xFF0B211C)
    val Outline = androidx.compose.ui.graphics.Color(0xFF8D736B)
    val Error = androidx.compose.ui.graphics.Color(0xFFBA1A1A)
    val ErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6)
    val OnErrorContainer = androidx.compose.ui.graphics.Color(0xFF410002)
    val OnDarkPrimary = androidx.compose.ui.graphics.Color(0xFF5E1707)
    val OnDarkSecondaryContainer = androidx.compose.ui.graphics.Color(0xFFD0E8E1)
    val OnDarkSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFE3BFB5)
    val DarkOutline = androidx.compose.ui.graphics.Color(0xFFAA8980)
    val DarkOutlineVariant = androidx.compose.ui.graphics.Color(0xFF5A4039)
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
