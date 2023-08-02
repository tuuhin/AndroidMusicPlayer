package com.example.musicplayer.presentation.routes

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.composables.MusicAlbumArt
import com.example.musicplayer.presentation.util.preview.FakeMusicModels
import com.example.musicplayer.presentation.util.preview.SongPreviewParameters
import com.example.musicplayer.presentation.util.rememberFormattedTimeFromLong
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongRoute(
    modifier: Modifier = Modifier,
    onSongEvents: (SongEvents) -> Unit,
    selectedSongState: CurrentSelectedSongState,
    totalDuration: Long,
    duration: Long,
    onNavigation: (@Composable () -> Unit)? = null,
) {

    val formattedTotalDuration = rememberFormattedTimeFromLong(time = totalDuration)
    val formattedDuration = rememberFormattedTimeFromLong(time = duration)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = onNavigation ?: {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedSongState.current?.let { song ->
                MusicAlbumArt(
                    internalPadding = PaddingValues(20.dp)
                )
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                song.artist?.let { artist ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Artist",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = artist,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                song.album?.let { album ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Album,
                            contentDescription = "Artist",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = album,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Slider(
                    value = 0.5f,
                    onValueChange = {},
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formattedDuration,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = formattedTotalDuration,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                }
                FloatingActionButton(
                    onClick = { onSongEvents(SongEvents.ToggleIsPlaying) },
                    elevation = FloatingActionButtonDefaults
                        .elevation(defaultElevation = 0.dp, pressedElevation = 4.dp)
                ) {
                    if (selectedSongState.isPlaying)
                        Icon(
                            imageVector = Icons.Outlined.Pause,
                            contentDescription = "Pause Current Song"
                        )
                    else
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            contentDescription = "Play now"
                        )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PlaySongRoutePreview(
    @PreviewParameter(SongPreviewParameters::class)
    songs: MusicResourceModel
) {
    MusicPlayerTheme {
        PlaySongRoute(
            onNavigation = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Arrow"
                )
            },
            selectedSongState = CurrentSelectedSongState(
                current = FakeMusicModels.fakeMusicResourceModel
            ),
            totalDuration = 18000L,
            duration = 0L, onSongEvents = {}
        )
    }
}