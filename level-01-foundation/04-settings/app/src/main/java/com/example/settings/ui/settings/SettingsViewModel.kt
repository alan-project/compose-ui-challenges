package com.example.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode
import com.example.settings.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class SettingsUiState(val preferences: SettingsPreferences)

class SettingsViewModel(
  private val settingsRepository: SettingsRepository,
) : ViewModel() {
  val uiState: StateFlow<SettingsUiState> =
    settingsRepository.preferences
      .map(::SettingsUiState)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsUiState(settingsRepository.preferences.value),
      )

  fun onNotificationsChanged(enabled: Boolean) = settingsRepository.setNotificationsEnabled(enabled)

  fun onBiometricChanged(enabled: Boolean) = settingsRepository.setBiometricEnabled(enabled)

  fun onDownloadOnWifiChanged(enabled: Boolean) = settingsRepository.setDownloadOnWifi(enabled)

  fun onAutoplayChanged(enabled: Boolean) = settingsRepository.setAutoplayVideos(enabled)

  fun onTextScaleChanged(scale: Float) = settingsRepository.setTextScale(scale)

  fun onThemeModeSelected(mode: ThemeMode) = settingsRepository.setThemeMode(mode)
}
