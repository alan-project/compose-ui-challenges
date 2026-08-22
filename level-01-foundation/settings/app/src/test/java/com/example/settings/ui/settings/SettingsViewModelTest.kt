package com.example.settings.ui.settings

import com.example.settings.data.model.ThemeMode
import com.example.settings.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class SettingsViewModelTest {
  @Test
  fun actions_updatePreferences() = runTest {
    val repository = SettingsRepositoryImpl()
    val viewModel = SettingsViewModel(repository)

    viewModel.onNotificationsChanged(false)
    viewModel.onThemeModeSelected(ThemeMode.DARK)
    viewModel.onTextScaleChanged(1.2f)

    val preferences = repository.preferences.first()
    assertFalse(preferences.notificationsEnabled)
    assertEquals(ThemeMode.DARK, preferences.themeMode)
    assertEquals(1.2f, preferences.textScale)
  }
}
