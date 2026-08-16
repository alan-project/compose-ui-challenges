package com.example.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.settings.data.model.ThemeMode
import com.example.settings.data.repository.SettingsRepositoryImpl
import com.example.settings.theme.SettingsTheme
import com.example.settings.ui.settings.SettingsRoute
import com.example.settings.ui.settings.SettingsViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      val viewModel =
        viewModel<SettingsViewModel> {
          SettingsViewModel(SettingsRepositoryImpl())
        }
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      val darkTheme =
        when (uiState.preferences.themeMode) {
          ThemeMode.SYSTEM -> isSystemInDarkTheme()
          ThemeMode.LIGHT -> false
          ThemeMode.DARK -> true
        }

      SettingsTheme(darkTheme = darkTheme) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          SettingsRoute(viewModel = viewModel)
        }
      }
    }
  }
}
