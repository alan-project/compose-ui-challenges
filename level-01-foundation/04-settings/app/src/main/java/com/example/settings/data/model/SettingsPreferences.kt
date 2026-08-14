package com.example.settings.data.model

enum class ThemeMode(val label: String) {
  SYSTEM("System"),
  LIGHT("Light"),
  DARK("Dark"),
}

data class SettingsPreferences(
  val displayName: String,
  val email: String,
  val membership: String,
  val notificationsEnabled: Boolean,
  val biometricEnabled: Boolean,
  val downloadOnWifi: Boolean,
  val autoplayVideos: Boolean,
  val textScale: Float,
  val themeMode: ThemeMode,
)
