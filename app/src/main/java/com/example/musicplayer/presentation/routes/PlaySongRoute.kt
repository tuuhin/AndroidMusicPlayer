package com.example.musicplayer.presentation.routes

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.musicplayer.presentation.composables.SongPlayerControls
import com.example.musicplayer.presentation.composables.SongPlayerSlider
import com.example.musicplayer.presentation.util.MusicTrackData
import com.example.musicplayer.presentation.util.preview.FakeMusicModels
import com.example.musicplayer.presentation.util.preview.SongPreviewParameters
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongRoute(
    modifier: Modifier = Modifier,
    onSongEvents: (SongEvents) -> Unit,
    songState: CurrentSelectedSongState,
    trackData: MusicTrackData,
    onNavigation: (@Composable () -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = onNavigation ?: {},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) { scPadding ->
        Column(
            modifier = modifier
                .padding(scPadding)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            songState.current?.let { song ->
                MusicAlbumArt(
                    internalPadding = PaddingValues(20.dp),
                    albumArt = song.albumArt,
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth(.75f)
                )
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                song.artist?.let { artist ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                Spacer(modifier = Modifier.height(12.dp))
                SongPlayerSlider(
                    duration = trackData.duration,
                    current = trackData.current,
                    ratio = trackData.ratio,
                    onSliderValueChange = { position ->
                        onSongEvents(SongEvents.OnTrackPositionChange(position))
                    }
                )
                SongPlayerControls(
                    isRepeating = songState.isRepeating,
                    isPlaying = songState.isPlaying,
                    onSongEvents = onSongEvents,
                    modifier = Modifier.fillMaxWidth()
                )
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
            songState = CurrentSelectedSongState(current = FakeMusicModels.fakeMusicResourceModel),
            trackData = MusicTrackData(current = 0f, duration = 1800f),
            onSongEvents = {}
        )
    }
}