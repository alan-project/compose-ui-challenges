package com.example.shoppingcart.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
    lightColorScheme(
        primary = Cobalt,
        onPrimary = OnCobalt,
        primaryContainer = CobaltMist,
        onPrimaryContainer = OnCobaltMist,
        secondary = GoldenOchre,
        onSecondary = OnGoldenOchre,
        secondaryContainer = Lemon,
        onSecondaryContainer = OnLemon,
        tertiary = Azure,
        onTertiary = OnAzure,
        tertiaryContainer = AzureMist,
        onTertiaryContainer = OnAzureMist,
        background = Canvas,
        onBackground = Ink,
        surface = White,
        onSurface = Ink,
        surfaceVariant = CoolSurface,
        onSurfaceVariant = MutedInk,
        outline = OutlineLight,
        outlineVariant = OutlineVariantLight,
        inverseSurface = Ink,
        inverseOnSurface = Canvas,
        inversePrimary = CobaltLight,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = CobaltLight,
        onPrimary = OnCobaltDark,
        primaryContainer = CobaltContainerDark,
        onPrimaryContainer = OnCobaltContainerDark,
        secondary = GoldenLight,
        onSecondary = OnGoldenDark,
        secondaryContainer = GoldenContainerDark,
        onSecondaryContainer = OnGoldenContainerDark,
        tertiary = AzureLight,
        onTertiary = OnAzureDark,
        tertiaryContainer = AzureContainerDark,
        onTertiaryContainer = OnAzureContainerDark,
        background = CanvasDark,
        onBackground = OnSurfaceDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = OutlineVariantDark,
        onSurfaceVariant = OnSurfaceVariantDark,
        outline = OutlineDark,
        outlineVariant = OutlineVariantDark,
        inverseSurface = OnSurfaceDark,
        inverseOnSurface = CanvasDark,
        inversePrimary = Cobalt,
    )

@Composable
fun ShoppingCartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ShoppingCartTypography,
        content = content,
    )
}
