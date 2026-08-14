package com.example.settings.data.repository

import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
  val preferences: StateFlow<SettingsPreferences>

  fun setNotificationsEnabled(enabled: Boolean)

  fun setBiometricEnabled(enabled: Boolean)

  fun setDownloadOnWifi(enabled: Boolean)

  fun setAutoplayVideos(enabled: Boolean)

  fun setTextScale(scale: Float)

  fun setThemeMode(mode: ThemeMode)
}
