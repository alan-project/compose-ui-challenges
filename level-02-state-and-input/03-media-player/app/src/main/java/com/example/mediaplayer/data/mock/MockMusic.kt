package com.example.mediaplayer.data.mock

import com.example.mediaplayer.data.model.AlbumArtwork
import com.example.mediaplayer.data.model.PlaybackState
import com.example.mediaplayer.data.model.Track

object MockMusic {
  val tracks =
    listOf(
      Track(
        id = "afterglow",
        title = "Afterglow",
        artist = "Nova Lane",
        album = "Chromatic Skies",
        durationMs = 222_000L,
        artwork = AlbumArtwork.AFTERGLOW,
      ),
      Track(
        id = "blue-hour",
        title = "Blue Hour",
        artist = "Lumen Park",
        album = "Paper Satellites",
        durationMs = 246_000L,
        artwork = AlbumArtwork.BLUE_HOUR,
      ),
      Track(
        id = "night-drive",
        title = "Night Drive",
        artist = "The Meridian",
        album = "Neon Geography",
        durationMs = 208_000L,
        artwork = AlbumArtwork.NIGHT_DRIVE,
      ),
    )

  val initialPlaybackState =
    PlaybackState(
      queue = tracks,
      currentIndex = 0,
      positionMs = 67_000L,
      favoriteTrackIds = setOf("blue-hour"),
    )
}
