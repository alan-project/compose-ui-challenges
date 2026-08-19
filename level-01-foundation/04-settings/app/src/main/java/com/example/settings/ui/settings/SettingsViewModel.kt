package com.example.settings.ui.settings

import androidx.lifecycle.ViewModel
import com.example.settings.data.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {}
