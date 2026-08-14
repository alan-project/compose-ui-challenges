package com.example.mediaplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediaplayer.data.repository.InMemoryMusicRepository
import com.example.mediaplayer.theme.MediaPlayerTheme
import com.example.mediaplayer.ui.player.MediaPlayerRoute
import com.example.mediaplayer.ui.player.MediaPlayerViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      MediaPlayerTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val viewModel =
            viewModel<MediaPlayerViewModel> {
              MediaPlayerViewModel(InMemoryMusicRepository())
            }

          MediaPlayerRoute(viewModel = viewModel)
        }
      }
    }
  }
}
