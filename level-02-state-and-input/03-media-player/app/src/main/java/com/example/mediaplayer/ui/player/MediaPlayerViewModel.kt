package com.example.mediaplayer.ui.player

import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.repository.MusicRepository

class MediaPlayerViewModel(
  private val musicRepository: MusicRepository,
) : ViewModel() {
}
