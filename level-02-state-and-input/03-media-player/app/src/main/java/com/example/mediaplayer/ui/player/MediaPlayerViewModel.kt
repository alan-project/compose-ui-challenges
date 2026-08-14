package com.example.mediaplayer.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaplayer.data.model.PlaybackState
import com.example.mediaplayer.data.model.RepeatMode
import com.example.mediaplayer.data.model.Track
import com.example.mediaplayer.data.repository.MusicRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class MediaPlayerUiState(
  val queue: List<Track>,
  val currentTrackIndex: Int,
  val positionMs: Long,
  val isPlaying: Boolean,
  val favoriteTrackIds: Set<String>,
  val isShuffleEnabled: Boolean,
  val repeatMode: RepeatMode,
) {
  val currentTrack: Track
    get() = queue[currentTrackIndex]

  val durationMs: Long
    get() = currentTrack.durationMs

  val isCurrentTrackFavorite: Boolean
    get() = currentTrack.id in favoriteTrackIds
}

class MediaPlayerViewModel(
  private val musicRepository: MusicRepository,
  private val playbackTickMs: Long = DEFAULT_PLAYBACK_TICK_MS,
) : ViewModel() {
  private var playbackJob: Job? = null

  init {
    require(playbackTickMs > 0L) { "The playback tick must be positive." }
  }

  val uiState: StateFlow<MediaPlayerUiState> =
    musicRepository.playbackState
      .map(PlaybackState::toUiState)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = musicRepository.playbackState.value.toUiState(),
      )

  init {
    startPlaybackTimerIfNeeded()
  }

  fun onPlayPause() {
    musicRepository.togglePlayback()
    if (musicRepository.playbackState.value.isPlaying) {
      startPlaybackTimerIfNeeded()
    } else {
      playbackJob?.cancel()
      playbackJob = null
    }
  }

  fun onSeek(positionMs: Long) = musicRepository.seekTo(positionMs)

  fun onNext() = musicRepository.skipToNext()

  fun onPrevious() = musicRepository.skipToPrevious()

  fun onFavoriteClick() = musicRepository.toggleCurrentFavorite()

  fun onShuffleClick() = musicRepository.toggleShuffle()

  fun onRepeatClick() = musicRepository.cycleRepeatMode()

  fun onTrackSelected(trackId: String) {
    musicRepository.selectTrack(trackId)
    startPlaybackTimerIfNeeded()
  }

  private fun startPlaybackTimerIfNeeded() {
    if (!musicRepository.playbackState.value.isPlaying || playbackJob?.isActive == true) return

    playbackJob =
      viewModelScope.launch {
        while (isActive && musicRepository.playbackState.value.isPlaying) {
          delay(playbackTickMs)
          musicRepository.advancePlayback(playbackTickMs)
        }
      }
  }

  companion object {
    const val DEFAULT_PLAYBACK_TICK_MS = 1_000L
  }
}

internal fun PlaybackState.toUiState(): MediaPlayerUiState =
  MediaPlayerUiState(
    queue = queue,
    currentTrackIndex = currentIndex,
    positionMs = positionMs,
    isPlaying = isPlaying,
    favoriteTrackIds = favoriteTrackIds,
    isShuffleEnabled = isShuffleEnabled,
    repeatMode = repeatMode,
  )
