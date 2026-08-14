package com.example.mediaplayer.ui.player

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediaplayer.R
import com.example.mediaplayer.data.mock.MockMusic
import com.example.mediaplayer.data.model.AlbumArtwork
import com.example.mediaplayer.data.model.RepeatMode
import com.example.mediaplayer.data.model.Track
import com.example.mediaplayer.theme.MediaPlayerTheme
import java.util.Locale

private val PlayerGlow = Color(0xFF0A2454)
private val PlayerInk = Color(0xFF05070D)
private val FrostedWhite = Color(0xFFF4F7FA)
private val MutedWhite = Color(0xFFAAB4C0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPlayerRoute(
  viewModel: MediaPlayerViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var isQueueVisible by rememberSaveable { mutableStateOf(false) }
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  MediaPlayerScreen(
    uiState = uiState,
    onPlayPause = viewModel::onPlayPause,
    onSeek = viewModel::onSeek,
    onPrevious = viewModel::onPrevious,
    onNext = viewModel::onNext,
    onFavoriteClick = viewModel::onFavoriteClick,
    onShuffleClick = viewModel::onShuffleClick,
    onRepeatClick = viewModel::onRepeatClick,
    onQueueClick = { isQueueVisible = true },
    modifier = modifier,
  )

  if (isQueueVisible) {
    ModalBottomSheet(
      onDismissRequest = { isQueueVisible = false },
      sheetState = sheetState,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.onSurface,
      dragHandle = {
        BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.onSurfaceVariant)
      },
    ) {
      QueueSheetContent(
        uiState = uiState,
        onTrackSelected = { trackId ->
          viewModel.onTrackSelected(trackId)
          isQueueVisible = false
        },
      )
    }
  }
}

@Composable
fun MediaPlayerScreen(
  uiState: MediaPlayerUiState,
  onPlayPause: () -> Unit,
  onSeek: (Long) -> Unit,
  onPrevious: () -> Unit,
  onNext: () -> Unit,
  onFavoriteClick: () -> Unit,
  onShuffleClick: () -> Unit,
  onRepeatClick: () -> Unit,
  onQueueClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier =
      modifier
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            colors = listOf(PlayerGlow, PlayerInk, MaterialTheme.colorScheme.background),
            endY = 1_300f,
          ),
        ),
  ) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      containerColor = Color.Transparent,
      contentWindowInsets = WindowInsets.safeDrawing,
    ) { innerPadding ->
      val layoutDirection = LocalLayoutDirection.current
      LazyColumn(
        modifier = Modifier.fillMaxSize().consumeWindowInsets(innerPadding),
        contentPadding =
          PaddingValues(
            start = 22.dp + innerPadding.calculateStartPadding(layoutDirection),
            top = 8.dp + innerPadding.calculateTopPadding(),
            end = 22.dp + innerPadding.calculateEndPadding(layoutDirection),
            bottom = 36.dp + innerPadding.calculateBottomPadding(),
          ),
        verticalArrangement = Arrangement.spacedBy(22.dp),
      ) {
        item {
          PlayerHeader(queueSize = uiState.queue.size, onQueueClick = onQueueClick)
        }
        item {
          AlbumArtwork(track = uiState.currentTrack)
        }
        item {
          TrackDetails(
            track = uiState.currentTrack,
            trackNumber = uiState.currentTrackIndex + 1,
            queueSize = uiState.queue.size,
            isFavorite = uiState.isCurrentTrackFavorite,
            onFavoriteClick = onFavoriteClick,
          )
        }
        item {
          PlaybackTimeline(
            positionMs = uiState.positionMs,
            durationMs = uiState.durationMs,
            onSeek = onSeek,
          )
        }
        item {
          PrimaryPlaybackControls(
            isPlaying = uiState.isPlaying,
            onPrevious = onPrevious,
            onPlayPause = onPlayPause,
            onNext = onNext,
          )
        }
        item {
          PlaybackModes(
            isShuffleEnabled = uiState.isShuffleEnabled,
            repeatMode = uiState.repeatMode,
            queueSize = uiState.queue.size,
            onShuffleClick = onShuffleClick,
            onQueueClick = onQueueClick,
            onRepeatClick = onRepeatClick,
          )
        }
      }
    }
  }
}

@Composable
private fun PlayerHeader(
  queueSize: Int,
  onQueueClick: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary) {
        Box(modifier = Modifier.size(34.dp), contentAlignment = Alignment.Center) {
          Text(
            text = "P",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
          )
        }
      }
      Column {
        Text(
          text = "PULSE",
          color = FrostedWhite,
          style = MaterialTheme.typography.labelLarge,
          letterSpacing = 1.8.sp,
        )
        Text(
          text = "NOW PLAYING",
          color = MutedWhite,
          style = MaterialTheme.typography.labelSmall,
          letterSpacing = 1.2.sp,
        )
      }
    }

    Surface(
      onClick = onQueueClick,
      modifier = Modifier.semantics { contentDescription = "Open queue, $queueSize tracks" },
      shape = CircleShape,
      color = Color.White.copy(alpha = 0.09f),
      contentColor = FrostedWhite,
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 13.dp, vertical = 9.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          painter = painterResource(R.drawable.ic_queue_music),
          contentDescription = null,
          modifier = Modifier.size(18.dp),
        )
        Text(text = queueSize.toString(), style = MaterialTheme.typography.labelLarge)
      }
    }
  }
}

@Composable
private fun AlbumArtwork(track: Track) {
  Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
    Box(
      modifier =
        Modifier.fillMaxWidth(0.91f)
          .widthIn(max = 380.dp)
          .aspectRatio(1f)
          .shadow(
            elevation = 28.dp,
            shape = RoundedCornerShape(32.dp),
            ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
            spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.30f),
          ),
    ) {
      Image(
        painter = painterResource(track.artwork.drawableResId),
        contentDescription = "Album artwork for ${track.title} by ${track.artist}",
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp)),
        contentScale = ContentScale.Crop,
      )
      Surface(
        modifier = Modifier.align(Alignment.TopStart).padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color.Black.copy(alpha = 0.44f),
        contentColor = Color.White,
      ) {
        Text(
          text = "LOSSLESS",
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
        )
      }
    }
  }
}

@Composable
private fun TrackDetails(
  track: Track,
  trackNumber: Int,
  queueSize: Int,
  isFavorite: Boolean,
  onFavoriteClick: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = track.title,
        color = FrostedWhite,
        style = MaterialTheme.typography.headlineMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = track.artist,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = "${track.album}  •  $trackNumber of $queueSize",
        color = MutedWhite,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
    IconButton(
      onClick = onFavoriteClick,
      modifier =
        Modifier.size(52.dp).semantics {
          selected = isFavorite
          stateDescription = if (isFavorite) "Favorited" else "Not favorited"
        },
    ) {
      Icon(
        painter =
          painterResource(
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
          ),
        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
        tint = if (isFavorite) MaterialTheme.colorScheme.tertiary else FrostedWhite,
      )
    }
  }
}

@Composable
private fun PlaybackTimeline(
  positionMs: Long,
  durationMs: Long,
  onSeek: (Long) -> Unit,
) {
  val boundedPosition = positionMs.coerceIn(0L, durationMs)
  Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
    Slider(
      value = boundedPosition.toFloat(),
      onValueChange = { onSeek(it.toLong()) },
      modifier =
        Modifier.fillMaxWidth().semantics {
          contentDescription = "Playback position"
          stateDescription =
            "${formatDuration(boundedPosition)} of ${formatDuration(durationMs)}"
        },
      valueRange = 0f..durationMs.coerceAtLeast(1L).toFloat(),
      colors =
        SliderDefaults.colors(
          thumbColor = MaterialTheme.colorScheme.primary,
          activeTrackColor = MaterialTheme.colorScheme.primary,
          inactiveTrackColor = MaterialTheme.colorScheme.outline,
        ),
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
      Text(
        text = formatDuration(boundedPosition),
        color = MutedWhite,
        style = MaterialTheme.typography.labelMedium,
      )
      Text(
        text = "−${formatDuration(durationMs - boundedPosition)}",
        color = MutedWhite,
        style = MaterialTheme.typography.labelMedium,
      )
    }
  }
}

@Composable
private fun PrimaryPlaybackControls(
  isPlaying: Boolean,
  onPrevious: () -> Unit,
  onPlayPause: () -> Unit,
  onNext: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(onClick = onPrevious, modifier = Modifier.size(64.dp)) {
      Icon(
        painter = painterResource(R.drawable.ic_previous),
        contentDescription = "Previous track",
        modifier = Modifier.size(34.dp),
        tint = FrostedWhite,
      )
    }
    Spacer(Modifier.width(18.dp))
    Surface(
      onClick = onPlayPause,
      modifier =
        Modifier.size(76.dp).semantics {
          role = Role.Button
          stateDescription = if (isPlaying) "Playing" else "Paused"
        },
      shape = CircleShape,
      color = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      shadowElevation = 12.dp,
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          painter = painterResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
          contentDescription = if (isPlaying) "Pause" else "Play",
          modifier = Modifier.size(36.dp),
        )
      }
    }
    Spacer(Modifier.width(18.dp))
    IconButton(onClick = onNext, modifier = Modifier.size(64.dp)) {
      Icon(
        painter = painterResource(R.drawable.ic_next),
        contentDescription = "Next track",
        modifier = Modifier.size(34.dp),
        tint = FrostedWhite,
      )
    }
  }
}

@Composable
private fun PlaybackModes(
  isShuffleEnabled: Boolean,
  repeatMode: RepeatMode,
  queueSize: Int,
  onShuffleClick: () -> Unit,
  onQueueClick: () -> Unit,
  onRepeatClick: () -> Unit,
) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(24.dp),
    color = Color.White.copy(alpha = 0.055f),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
      ModeControl(
        iconResId = R.drawable.ic_shuffle,
        label = "Shuffle",
        contentDescription = if (isShuffleEnabled) "Turn shuffle off" else "Turn shuffle on",
        selected = isShuffleEnabled,
        onClick = onShuffleClick,
        modifier = Modifier.weight(1f),
      )
      ModeControl(
        iconResId = R.drawable.ic_queue_music,
        label = "Queue $queueSize",
        contentDescription = "Open queue, $queueSize tracks",
        selected = null,
        onClick = onQueueClick,
        modifier = Modifier.weight(1f),
      )
      ModeControl(
        iconResId =
          if (repeatMode == RepeatMode.ONE) R.drawable.ic_repeat_one else R.drawable.ic_repeat,
        label =
          when (repeatMode) {
            RepeatMode.OFF -> "Repeat"
            RepeatMode.ALL -> "Repeat all"
            RepeatMode.ONE -> "Repeat one"
          },
        contentDescription = "Change repeat mode",
        selected = repeatMode != RepeatMode.OFF,
        stateDescription = repeatMode.accessibilityLabel,
        onClick = onRepeatClick,
        modifier = Modifier.weight(1f),
      )
    }
  }
}

@Composable
private fun ModeControl(
  @DrawableRes iconResId: Int,
  label: String,
  contentDescription: String,
  selected: Boolean?,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  stateDescription: String? = selected?.let { if (it) "On" else "Off" },
) {
  val contentColor = if (selected == true) MaterialTheme.colorScheme.secondary else MutedWhite
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(1.dp),
  ) {
    IconButton(
      onClick = onClick,
      modifier =
        Modifier.semantics {
          if (selected != null) this.selected = selected
          if (stateDescription != null) this.stateDescription = stateDescription
        },
    ) {
      Icon(
        painter = painterResource(iconResId),
        contentDescription = contentDescription,
        tint = contentColor,
      )
    }
    Text(
      text = label,
      color = contentColor,
      style = MaterialTheme.typography.labelSmall,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
private fun QueueSheetContent(
  uiState: MediaPlayerUiState,
  onTrackSelected: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.fillMaxWidth(),
    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 18.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
          Text(
            text = "Up next",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium,
          )
          Text(
            text = "From your night mix",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        Text(
          text = "${uiState.queue.size} TRACKS",
          color = MaterialTheme.colorScheme.secondary,
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
        )
      }
    }

    itemsIndexed(uiState.queue, key = { _, track -> track.id }) { index, track ->
      QueueTrackRow(
        track = track,
        isCurrent = index == uiState.currentTrackIndex,
        isPlaying = uiState.isPlaying && index == uiState.currentTrackIndex,
        onClick = { onTrackSelected(track.id) },
      )
    }
  }
}

@Composable
private fun QueueTrackRow(
  track: Track,
  isCurrent: Boolean,
  isPlaying: Boolean,
  onClick: () -> Unit,
) {
  Row(
    modifier =
      Modifier.fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(
          if (isCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
          else Color.Transparent,
        )
        .clickable(onClick = onClick)
        .semantics(mergeDescendants = true) {
          selected = isCurrent
          role = Role.Button
          stateDescription = if (isCurrent) "Current track" else "Queued"
        }
        .padding(10.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Image(
      painter = painterResource(track.artwork.drawableResId),
      contentDescription = null,
      modifier = Modifier.size(58.dp).clip(RoundedCornerShape(15.dp)),
      contentScale = ContentScale.Crop,
    )
    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
      Text(
        text = track.title,
        color =
          if (isCurrent) MaterialTheme.colorScheme.primary
          else MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = "${track.artist}  •  ${track.album}",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
    if (isCurrent) {
      PlayingIndicator(isPlaying = isPlaying)
    } else {
      Text(
        text = formatDuration(track.durationMs),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.labelMedium,
      )
    }
  }
}

@Composable
private fun PlayingIndicator(isPlaying: Boolean) {
  Row(
    modifier = Modifier.size(width = 24.dp, height = 20.dp),
    horizontalArrangement = Arrangement.spacedBy(3.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val heights = if (isPlaying) listOf(10.dp, 19.dp, 14.dp) else listOf(4.dp, 4.dp, 4.dp)
    heights.forEach { height ->
      Box(
        modifier =
          Modifier.width(4.dp)
            .height(height)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
      )
    }
  }
}

private val AlbumArtwork.drawableResId: Int
  @DrawableRes
  get() =
    when (this) {
      AlbumArtwork.AFTERGLOW -> R.drawable.album_afterglow
      AlbumArtwork.BLUE_HOUR -> R.drawable.album_blue_hour
      AlbumArtwork.NIGHT_DRIVE -> R.drawable.album_night_drive
    }

private val RepeatMode.accessibilityLabel: String
  get() =
    when (this) {
      RepeatMode.OFF -> "Repeat off"
      RepeatMode.ALL -> "Repeat all"
      RepeatMode.ONE -> "Repeat one"
    }

private fun formatDuration(durationMs: Long): String {
  val totalSeconds = durationMs.coerceAtLeast(0L) / 1_000L
  return String.format(Locale.US, "%d:%02d", totalSeconds / 60L, totalSeconds % 60L)
}

@Preview(name = "Player", showBackground = true, backgroundColor = 0xFF05070D)
@Composable
private fun MediaPlayerScreenPreview() {
  MediaPlayerTheme {
    MediaPlayerScreen(
      uiState = MockMusic.initialPlaybackState.toUiState(),
      onPlayPause = {},
      onSeek = {},
      onPrevious = {},
      onNext = {},
      onFavoriteClick = {},
      onShuffleClick = {},
      onRepeatClick = {},
      onQueueClick = {},
    )
  }
}

@Preview(name = "Queue", showBackground = true, backgroundColor = 0xFF111722)
@Composable
private fun QueueSheetPreview() {
  MediaPlayerTheme {
    Surface(color = MaterialTheme.colorScheme.surface) {
      QueueSheetContent(
        uiState = MockMusic.initialPlaybackState.toUiState(),
        onTrackSelected = {},
        modifier = Modifier.padding(top = 24.dp),
      )
    }
  }
}
