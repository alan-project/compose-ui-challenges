package com.example.settings.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.settings.R
import com.example.settings.data.mock.MockSettings
import com.example.settings.data.model.SettingsPreferences
import com.example.settings.data.model.ThemeMode
import com.example.settings.theme.SettingsTheme
import kotlin.math.roundToInt

@Composable
fun SettingsRoute(
  viewModel: SettingsViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  SettingsScreen(
    preferences = uiState.preferences,
    onNotificationsChanged = viewModel::onNotificationsChanged,
    onBiometricChanged = viewModel::onBiometricChanged,
    onDownloadOnWifiChanged = viewModel::onDownloadOnWifiChanged,
    onAutoplayChanged = viewModel::onAutoplayChanged,
    onTextScaleChanged = viewModel::onTextScaleChanged,
    onThemeModeSelected = viewModel::onThemeModeSelected,
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  preferences: SettingsPreferences,
  onNotificationsChanged: (Boolean) -> Unit,
  onBiometricChanged: (Boolean) -> Unit,
  onDownloadOnWifiChanged: (Boolean) -> Unit,
  onAutoplayChanged: (Boolean) -> Unit,
  onTextScaleChanged: (Float) -> Unit,
  onThemeModeSelected: (ThemeMode) -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = { CenterAlignedTopAppBar(title = { Text("Settings", fontWeight = FontWeight.Bold) }) },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      contentPadding = PaddingValues(20.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      item { ProfileCard(preferences) }
      item { SectionTitle("Account & security") }
      item {
        SettingsCard {
          SettingSwitchRow("Push notifications", "News, reminders, and account updates", preferences.notificationsEnabled, onNotificationsChanged)
          SettingSwitchRow("Biometric unlock", "Use face or fingerprint to unlock", preferences.biometricEnabled, onBiometricChanged)
        }
      }
      item { SectionTitle("Playback & downloads") }
      item {
        SettingsCard {
          SettingSwitchRow("Download on Wi-Fi only", "Protect your mobile data", preferences.downloadOnWifi, onDownloadOnWifiChanged)
          SettingSwitchRow("Autoplay videos", "Play previews while browsing", preferences.autoplayVideos, onAutoplayChanged)
        }
      }
      item { SectionTitle("Appearance") }
      item {
        SettingsCard {
          Text("Theme", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeMode.entries.forEach { mode ->
              FilterChip(selected = preferences.themeMode == mode, onClick = { onThemeModeSelected(mode) }, label = { Text(mode.label) })
            }
          }
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Text size", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("${(preferences.textScale * 100).roundToInt()}%", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
          }
          Slider(value = preferences.textScale, onValueChange = onTextScaleChanged, valueRange = 0.8f..1.4f, steps = 5)
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("A", style = MaterialTheme.typography.bodySmall)
            Text("A", style = MaterialTheme.typography.titleLarge)
          }
        }
      }
    }
  }
}

@Composable
private fun ProfileCard(preferences: SettingsPreferences) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    shape = RoundedCornerShape(24.dp),
  ) {
    Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
      Image(
        painter = painterResource(R.drawable.profile_maya),
        contentDescription = "${preferences.displayName} profile photo",
        modifier = Modifier.size(84.dp).clip(CircleShape),
        contentScale = ContentScale.Crop,
      )
      Spacer(Modifier.width(16.dp))
      Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(preferences.displayName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(preferences.email, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f), style = MaterialTheme.typography.bodyMedium)
        Text(preferences.membership, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
  Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer), shape = RoundedCornerShape(20.dp)) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp), content = content)
  }
}

@Composable
private fun SettingSwitchRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
  Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
      Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
      Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Switch(checked = checked, onCheckedChange = onCheckedChange)
  }
}

@Composable
private fun SectionTitle(title: String) {
  Text(title.uppercase(), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
  SettingsTheme { SettingsScreen(MockSettings.preferences, {}, {}, {}, {}, {}, {}) }
}
