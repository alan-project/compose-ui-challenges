package com.example.mediaplayer.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = Lavender,
    onPrimary = DeepPlum,
    primaryContainer = LavenderContainer,
    onPrimaryContainer = Cloud,
    secondary = Mint,
    onSecondary = Ink,
    background = Ink,
    onBackground = Cloud,
    surface = NightSurface,
    onSurface = Cloud,
    surfaceVariant = ColorTokens.SurfaceVariant,
    onSurfaceVariant = Mist,
    outline = ColorTokens.Outline,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = ColorTokens.LightPrimary,
    onPrimary = LightSurface,
    primaryContainer = ColorTokens.LightPrimaryContainer,
    onPrimaryContainer = DeepPlum,
    secondary = ColorTokens.LightSecondary,
    background = LightBackground,
    onBackground = LightInk,
    surface = LightSurface,
    onSurface = LightInk,
    onSurfaceVariant = ColorTokens.LightMuted,
  )

private object ColorTokens {
  val SurfaceVariant = androidx.compose.ui.graphics.Color(0xFF24212C)
  val Outline = androidx.compose.ui.graphics.Color(0xFF3D3845)
  val LightPrimary = androidx.compose.ui.graphics.Color(0xFF704B8D)
  val LightPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFF0D8FF)
  val LightSecondary = androidx.compose.ui.graphics.Color(0xFF286B60)
  val LightMuted = androidx.compose.ui.graphics.Color(0xFF6D6470)
}

@Composable
fun MediaPlayerTheme(
  darkTheme: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
