package com.example.settings.data.repository

import com.example.settings.data.mock.MockSettings
import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemorySettingsRepository : SettingsRepository {
  private val _preferences = MutableStateFlow(MockSettings.preferences)

  override val preferences: StateFlow<SettingsPreferences> = _preferences.asStateFlow()

  override fun setNotificationsEnabled(enabled: Boolean) {
    _preferences.update { it.copy(notificationsEnabled = enabled) }
  }

  override fun setBiometricEnabled(enabled: Boolean) {
    _preferences.update { it.copy(biometricEnabled = enabled) }
  }

  override fun setDownloadOnWifi(enabled: Boolean) {
    _preferences.update { it.copy(downloadOnWifi = enabled) }
  }

  override fun setAutoplayVideos(enabled: Boolean) {
    _preferences.update { it.copy(autoplayVideos = enabled) }
  }

  override fun setTextScale(scale: Float) {
    _preferences.update { it.copy(textScale = scale.coerceIn(0.8f, 1.4f)) }
  }

  override fun setThemeMode(mode: ThemeMode) {
    _preferences.update { it.copy(themeMode = mode) }
  }
}
