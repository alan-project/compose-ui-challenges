package com.example.settings.data.mock

import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode

object MockSettings {
    val preferences = SettingsPreferences(
        displayName = "Alan Project",
        email = "alan.project@example.com",
        membership = "Premium member",
        notificationsEnabled = true,
        biometricEnabled = false,
        backgroundSyncEnabled = true,
        importantNotificationsOnly = true,
        textScale = 1f,
        themeMode = ThemeMode.SYSTEM,
    )
}
