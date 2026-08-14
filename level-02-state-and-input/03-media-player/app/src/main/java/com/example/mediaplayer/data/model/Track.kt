package com.example.mediaplayer.data.model

enum class AlbumArtwork {
  AFTERGLOW,
  BLUE_HOUR,
  NIGHT_DRIVE,
}

data class Track(
  val id: String,
  val title: String,
  val artist: String,
  val album: String,
  val durationMs: Long,
  val artwork: AlbumArtwork,
) {
  init {
    require(durationMs > 0L) { "A track duration must be positive." }
  }
}
