package com.example.settings.data.repository

import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
  val preferences: Flow<SettingsPreferences>

  fun setNotificationsEnabled(enabled: Boolean)

  fun setBiometricEnabled(enabled: Boolean)

  fun setBackgroundSyncEnabled(enabled: Boolean)

    fun setImportantNotificationsOnly(enabled: Boolean)

  fun setTextScale(scale: Float)

  fun setThemeMode(mode: ThemeMode)
}
