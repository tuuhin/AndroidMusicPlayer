package com.example.musicplayer.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.SelectSongEvents
import com.example.musicplayer.presentation.util.SelectedSongState
import com.example.musicplayer.presentation.util.SongPreviewParameters

@Composable
fun MusicPlayerBar(
    onPlayPause: (SelectSongEvents) -> Unit,
    currentSelectedSong: SelectedSongState,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    tonalElevation: Dp = 10.dp
) {
    // if song is playing then show the selected song otherwise not
    // make sure to uncomment this out
    // check(isSongPlaying && selectedSong != null)

    BottomAppBar(
        modifier = modifier.clip(
            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ),
        windowInsets = WindowInsets.navigationBars,
        contentPadding = PaddingValues(4.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(.8f)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentSelectedSong.current?.let { song ->
                MusicAlbumArt(
                    albumArt = currentSelectedSong.current.albumArt,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )
                Column(
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Text(
                        text = song.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelLarge
                    )
                    song.artist?.let { artist ->
                        Text(
                            text = artist,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium
                        )
                    } ?: song.album?.let { album ->
                        Text(
                            text = album,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { onPlayPause(SelectSongEvents.ToggleIsPlaying) },
            colors = IconButtonDefaults
                .outlinedIconButtonColors(contentColor = contentColor),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            if (currentSelectedSong.isPlaying)
                Icon(
                    imageVector = Icons.Outlined.Pause,
                    contentDescription = "Pause",
                    modifier = Modifier.size(32.dp)
                )
            else Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = "Play Current",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}


@Preview
@Composable
fun MusicPlayerBarPreview(
    @PreviewParameter(SongPreviewParameters::class)
    song: MusicResourceModel
) {
    MusicPlayerBar(
        currentSelectedSong = SelectedSongState(current = song),
        onPlayPause = {},
    )
}