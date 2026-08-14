package com.example.mediaplayer.data.model

enum class RepeatMode {
  OFF,
  ALL,
  ONE,
}

data class PlaybackState(
  val queue: List<Track>,
  val currentIndex: Int = 0,
  val positionMs: Long = 0L,
  val isPlaying: Boolean = false,
  val favoriteTrackIds: Set<String> = emptySet(),
  val isShuffleEnabled: Boolean = false,
  val repeatMode: RepeatMode = RepeatMode.OFF,
) {
  init {
    require(queue.isNotEmpty()) { "The playback queue cannot be empty." }
    require(currentIndex in queue.indices) { "The current index must point to a queued track." }
  }

  val currentTrack: Track
    get() = queue[currentIndex]
}
