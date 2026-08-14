package com.example.shoppingcart.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
    lightColorScheme(
        primary = Evergreen,
        onPrimary = OnEvergreen,
        primaryContainer = MintCream,
        onPrimaryContainer = OnMintCream,
        secondary = Terracotta,
        onSecondary = OnTerracotta,
        secondaryContainer = PeachCream,
        onSecondaryContainer = OnPeachCream,
        tertiary = Mulberry,
        onTertiary = Color.White,
        tertiaryContainer = LavenderCream,
        onTertiaryContainer = Color(0xFF2C122C),
        background = Parchment,
        onBackground = Ink,
        surface = Porcelain,
        onSurface = Ink,
        surfaceVariant = Linen,
        onSurfaceVariant = MutedInk,
        outline = OutlineLight,
        outlineVariant = Color(0xFFC4C8C2),
        inverseSurface = Color(0xFF303330),
        inverseOnSurface = Color(0xFFF1F1EC),
        inversePrimary = EvergreenDark,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = EvergreenDark,
        onPrimary = OnEvergreenDark,
        primaryContainer = EvergreenContainerDark,
        onPrimaryContainer = OnEvergreenContainerDark,
        secondary = TerracottaDark,
        onSecondary = OnTerracottaDark,
        secondaryContainer = TerracottaContainerDark,
        onSecondaryContainer = OnTerracottaContainerDark,
        tertiary = MulberryDark,
        onTertiary = Color(0xFF422640),
        tertiaryContainer = Color(0xFF5A3B57),
        onTertiaryContainer = Color(0xFFFFD7F8),
        background = CanvasDark,
        onBackground = OnSurfaceDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = Color(0xFF414743),
        onSurfaceVariant = OnSurfaceVariantDark,
        outline = OutlineDark,
        outlineVariant = OutlineVariantDark,
        inverseSurface = Color(0xFFE2E4DE),
        inverseOnSurface = Color(0xFF2E312E),
        inversePrimary = Evergreen,
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
