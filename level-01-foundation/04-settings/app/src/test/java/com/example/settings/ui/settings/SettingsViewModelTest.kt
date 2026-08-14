package com.example.settings.ui.settings

import com.example.settings.data.model.ThemeMode
import com.example.settings.data.repository.InMemorySettingsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class SettingsViewModelTest {
  @Test
  fun actions_updatePreferences() {
    val repository = InMemorySettingsRepository()
    val viewModel = SettingsViewModel(repository)

    viewModel.onNotificationsChanged(false)
    viewModel.onThemeModeSelected(ThemeMode.DARK)
    viewModel.onTextScaleChanged(1.2f)

    assertFalse(repository.preferences.value.notificationsEnabled)
    assertEquals(ThemeMode.DARK, repository.preferences.value.themeMode)
    assertEquals(1.2f, repository.preferences.value.textScale)
  }
}
