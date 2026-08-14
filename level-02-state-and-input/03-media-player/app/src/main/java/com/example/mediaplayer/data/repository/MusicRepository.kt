package com.example.mediaplayer.data.repository

import com.example.mediaplayer.data.model.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface MusicRepository {
  val playbackState: StateFlow<PlaybackState>

  fun togglePlayback()

  fun seekTo(positionMs: Long)

  fun skipToNext()

  fun skipToPrevious()

  fun toggleCurrentFavorite()

  fun toggleShuffle()

  fun cycleRepeatMode()

  fun selectTrack(trackId: String)

  fun advancePlayback(elapsedMs: Long)
}
