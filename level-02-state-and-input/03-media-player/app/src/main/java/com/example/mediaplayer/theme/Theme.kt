package com.example.mediaplayer.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = AcidLime,
    onPrimary = OnAcidLime,
    primaryContainer = LimeContainer,
    onPrimaryContainer = OnLimeContainer,
    secondary = ElectricCyan,
    onSecondary = OnElectricCyan,
    secondaryContainer = CyanContainer,
    onSecondaryContainer = OnCyanContainer,
    tertiary = HotPink,
    onTertiary = OnHotPink,
    tertiaryContainer = PinkContainer,
    onTertiaryContainer = OnPinkContainer,
    background = SignalBlack,
    onBackground = Frost,
    surface = SignalSurface,
    onSurface = Frost,
    surfaceVariant = SignalSurfaceVariant,
    onSurfaceVariant = Mist,
    outline = SignalOutline,
  )

@Composable
fun MediaPlayerTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = DarkColorScheme, typography = Typography, content = content)
}
