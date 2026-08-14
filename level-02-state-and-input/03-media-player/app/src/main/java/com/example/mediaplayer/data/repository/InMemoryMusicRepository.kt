package com.example.mediaplayer.data.repository

import com.example.mediaplayer.data.mock.MockMusic
import com.example.mediaplayer.data.model.PlaybackState
import com.example.mediaplayer.data.model.RepeatMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryMusicRepository(
  initialState: PlaybackState = MockMusic.initialPlaybackState,
  private val shuffledOrderProvider: (List<Int>) -> List<Int> = { indices -> indices.shuffled() },
) : MusicRepository {
  private val _playbackState = MutableStateFlow(initialState.normalized())
  override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
  private var shuffledOrder: List<Int> =
    if (initialState.isShuffleEnabled) createShuffledOrder(initialState.queue.indices.toList())
    else emptyList()

  override fun togglePlayback() {
    _playbackState.update { state ->
      val restartAtBeginning = state.positionMs >= state.currentTrack.durationMs
      state.copy(
        positionMs = if (restartAtBeginning) 0L else state.positionMs,
        isPlaying = !state.isPlaying,
      )
    }
  }

  override fun seekTo(positionMs: Long) {
    _playbackState.update { state ->
      state.copy(positionMs = positionMs.coerceIn(0L, state.currentTrack.durationMs))
    }
  }

  override fun skipToNext() {
    _playbackState.update { state ->
      state.copy(currentIndex = nextIndex(state, wrap = true) ?: state.currentIndex, positionMs = 0L)
    }
  }

  override fun skipToPrevious() {
    _playbackState.update { state ->
      state.copy(currentIndex = previousIndex(state), positionMs = 0L)
    }
  }

  override fun toggleCurrentFavorite() {
    _playbackState.update { state ->
      val currentId = state.currentTrack.id
      val favorites =
        if (currentId in state.favoriteTrackIds) {
          state.favoriteTrackIds - currentId
        } else {
          state.favoriteTrackIds + currentId
        }
      state.copy(favoriteTrackIds = favorites)
    }
  }

  override fun toggleShuffle() {
    _playbackState.update { state ->
      val isEnabled = !state.isShuffleEnabled
      shuffledOrder =
        if (isEnabled) createShuffledOrder(state.queue.indices.toList())
        else emptyList()
      state.copy(isShuffleEnabled = isEnabled)
    }
  }

  override fun cycleRepeatMode() {
    _playbackState.update { state ->
      val nextMode =
        when (state.repeatMode) {
          RepeatMode.OFF -> RepeatMode.ALL
          RepeatMode.ALL -> RepeatMode.ONE
          RepeatMode.ONE -> RepeatMode.OFF
        }
      state.copy(repeatMode = nextMode)
    }
  }

  override fun selectTrack(trackId: String) {
    _playbackState.update { state ->
      val selectedIndex = state.queue.indexOfFirst { it.id == trackId }
      if (selectedIndex == -1) {
        state
      } else {
        state.copy(currentIndex = selectedIndex, positionMs = 0L, isPlaying = true)
      }
    }
  }

  override fun advancePlayback(elapsedMs: Long) {
    if (elapsedMs <= 0L) return

    _playbackState.update { original ->
      if (!original.isPlaying) return@update original

      var state = original
      var newPosition = state.positionMs + elapsedMs
      var keepAdvancing = true

      while (keepAdvancing && newPosition >= state.currentTrack.durationMs) {
        val overflow = newPosition - state.currentTrack.durationMs
        when (state.repeatMode) {
          RepeatMode.ONE -> {
            newPosition = overflow % state.currentTrack.durationMs
            keepAdvancing = false
          }

          RepeatMode.ALL -> {
            state = state.copy(currentIndex = nextIndex(state, wrap = true) ?: 0)
            newPosition = overflow
          }

          RepeatMode.OFF -> {
            val nextIndex = nextIndex(state, wrap = false)
            if (nextIndex == null) {
              newPosition = state.currentTrack.durationMs
              state = state.copy(isPlaying = false)
              keepAdvancing = false
            } else {
              state = state.copy(currentIndex = nextIndex)
              newPosition = overflow
            }
          }
        }
      }

      state.copy(positionMs = newPosition)
    }
  }

  private fun nextIndex(state: PlaybackState, wrap: Boolean): Int? {
    val order = playbackOrder(state)
    val currentOrderIndex = order.indexOf(state.currentIndex)
    return when {
      currentOrderIndex < order.lastIndex -> order[currentOrderIndex + 1]
      wrap -> order.first()
      else -> null
    }
  }

  private fun previousIndex(state: PlaybackState): Int {
    val order = playbackOrder(state)
    val currentOrderIndex = order.indexOf(state.currentIndex)
    return if (currentOrderIndex > 0) order[currentOrderIndex - 1] else order.last()
  }

  private fun playbackOrder(state: PlaybackState): List<Int> {
    val indices = state.queue.indices.toList()
    if (!state.isShuffleEnabled) return indices

    if (shuffledOrder.size != indices.size || shuffledOrder.toSet() != indices.toSet()) {
      shuffledOrder = createShuffledOrder(indices)
    }
    return shuffledOrder
  }

  private fun createShuffledOrder(indices: List<Int>): List<Int> {
    val candidate = shuffledOrderProvider(indices)
    require(candidate.size == indices.size && candidate.toSet() == indices.toSet()) {
      "The shuffled order must contain every queue index exactly once."
    }

    return if (candidate == indices && candidate.size > 1) {
      candidate.drop(1) + candidate.first()
    } else {
      candidate
    }
  }

  private fun PlaybackState.normalized(): PlaybackState =
    copy(positionMs = positionMs.coerceIn(0L, currentTrack.durationMs))
}
