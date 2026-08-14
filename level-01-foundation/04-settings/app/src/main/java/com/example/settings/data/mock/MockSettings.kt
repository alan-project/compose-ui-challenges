package com.example.settings.data.mock

import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode

object MockSettings {
  val preferences =
    SettingsPreferences(
      displayName = "Maya Chen",
      email = "maya.chen@example.com",
      membership = "Premium member",
      notificationsEnabled = true,
      biometricEnabled = false,
      downloadOnWifi = true,
      autoplayVideos = false,
      textScale = 1f,
      themeMode = ThemeMode.SYSTEM,
    )
}
