package com.example.musicplayer.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.presentation.util.preview.CurrentSongPreviewParams
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun MusicPlayerBar(
    onSongEvents: (SongEvents) -> Unit,
    currentSong: CurrentSelectedSongState,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    tonalElevation: Dp = 10.dp,
    density: Density = LocalDensity.current,
) {
    // if song is playing then show the selected song otherwise not
    // make sure to uncomment this out
    // check(isSongPlaying && selectedSong != null)

    AnimatedVisibility(
        visible = currentSong.showBottomBar && currentSong.current != null,
        enter = slideInVertically { with(density) { 60.dp.roundToPx() } }
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically { with(density) { 60.dp.roundToPx() } }
                + fadeOut(), modifier = Modifier.clip(
            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        )
    ) {
        BottomAppBar(
            modifier = modifier,
            windowInsets = WindowInsets.navigationBars,
            containerColor = containerColor,
            contentPadding = PaddingValues(4.dp),
            contentColor = contentColor,
            tonalElevation = tonalElevation
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                currentSong.current?.let { song ->
                    MusicPlayerBarContent(
                        song = song,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 2.dp)
                            .weight(.75f),
                    )
                }
                IconButton(
                    onClick = { onSongEvents(SongEvents.ToggleIsRepeating) },
                    colors = IconButtonDefaults
                        .outlinedIconButtonColors(contentColor = contentColor),
                ) {
                    when {
                        currentSong.isRepeating -> Icon(
                            painter = painterResource(id = R.drawable.ic_repeat),
                            contentDescription = "Repeating",
                            modifier = Modifier.size(28.dp)
                        )

                        else -> Icon(
                            painter = painterResource(id = R.drawable.ic_no_repeat),
                            contentDescription = "Not repeating",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                IconButton(
                    onClick = { onSongEvents(SongEvents.ToggleIsPlaying) },
                    colors = IconButtonDefaults
                        .outlinedIconButtonColors(contentColor = contentColor),
                ) {
                    when {
                        currentSong.isPlaying -> Icon(
                            painter = painterResource(id = R.drawable.ic_pause),
                            contentDescription = "Pause",
                            modifier = Modifier.size(32.dp)
                        )

                        else -> Icon(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "Play Current",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MusicPlayerBarPreview(
    @PreviewParameter(CurrentSongPreviewParams::class)
    song: CurrentSelectedSongState
) {
    MusicPlayerTheme {
        MusicPlayerBar(
            currentSong = song,
            onSongEvents = {},
        )
    }
}