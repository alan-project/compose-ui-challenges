package com.example.productdetail.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
  lightColorScheme(
    primary = Vermilion,
    onPrimary = OnVermilion,
    primaryContainer = VermilionContainer,
    onPrimaryContainer = OnVermilionContainer,
    secondary = DeepNavy,
    onSecondary = OnDeepNavy,
    secondaryContainer = NavyContainer,
    onSecondaryContainer = OnNavyContainer,
    tertiary = EditorialGold,
    onTertiary = OnEditorialGold,
    tertiaryContainer = GoldContainer,
    onTertiaryContainer = OnGoldContainer,
    background = EditorialCream,
    onBackground = Graphite,
    surface = EditorialWhite,
    onSurface = Graphite,
    surfaceVariant = EditorialSurfaceVariant,
    onSurfaceVariant = MutedEspresso,
    outline = EditorialOutline,
    outlineVariant = EditorialOutlineVariant,
    inverseSurface = Espresso,
    inverseOnSurface = OnEspresso,
    inversePrimary = VermilionDark,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = VermilionDark,
    onPrimary = OnVermilionDark,
    primaryContainer = VermilionContainerDark,
    onPrimaryContainer = OnVermilionContainerDark,
    secondary = DeepNavyDark,
    onSecondary = OnDeepNavyDark,
    secondaryContainer = NavyContainerDark,
    onSecondaryContainer = OnNavyContainerDark,
    tertiary = EditorialGoldDark,
    onTertiary = OnEditorialGoldDark,
    tertiaryContainer = GoldContainerDark,
    onTertiaryContainer = OnGoldContainerDark,
    background = EditorialNight,
    onBackground = EditorialOnSurfaceDark,
    surface = EditorialSurfaceDark,
    onSurface = EditorialOnSurfaceDark,
    surfaceVariant = EditorialSurfaceVariantDark,
    onSurfaceVariant = EditorialOnSurfaceVariantDark,
    outline = EditorialOutlineDark,
    outlineVariant = EditorialOutlineVariantDark,
    inverseSurface = EditorialOnSurfaceDark,
    inverseOnSurface = EditorialNight,
    inversePrimary = Vermilion,
  )

@Composable
fun ProductDetailTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content,
  )
}
