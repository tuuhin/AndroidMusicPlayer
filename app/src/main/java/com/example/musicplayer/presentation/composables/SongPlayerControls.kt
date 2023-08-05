package com.example.musicplayer.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.utils.MediaSessionConstants

@Composable
fun SongPlayerControls(
    isRepeating: Boolean,
    isPlaying: Boolean,
    onSongEvents: (SongEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledTonalIconButton(
                onClick = { onSongEvents(SongEvents.RewindCurrentMedia) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.align(Alignment.CenterVertically),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_rewind_by_10),
                    contentDescription = MediaSessionConstants.REWIND_BY_10_SEC
                )
            }
            FloatingActionButton(
                onClick = { onSongEvents(SongEvents.ToggleIsPlaying) },
                elevation = FloatingActionButtonDefaults
                    .elevation(defaultElevation = 0.dp, pressedElevation = 4.dp),
                modifier = Modifier.align(Alignment.CenterVertically),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                if (isPlaying)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pause),
                        contentDescription = "Pause Current Song"
                    )
                else
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Play now"
                    )
            }
            FilledTonalIconButton(
                onClick = { onSongEvents(SongEvents.ForwardCurrentMedia) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.align(Alignment.CenterVertically),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward_by_10),
                    contentDescription = MediaSessionConstants.FORWARD_BY_10_SEC
                )
            }
        }
        FilledTonalIconButton(
            onClick = { onSongEvents(SongEvents.ToggleIsRepeating) },
            shape = MaterialTheme.shapes.medium,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            if (isRepeating)
                Icon(
                    painter = painterResource(id = R.drawable.ic_repeat),
                    contentDescription = MediaSessionConstants.DO_NOT_REPEAT_CURRENT_SONG
                )
            else Icon(
                painter = painterResource(id = R.drawable.ic_no_repeat),
                contentDescription = MediaSessionConstants.REPEAT_CURRENT_SONG
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SongPlayerControlsPreview() {
    MusicPlayerTheme {
        SongPlayerControls(
            isPlaying = true,
            isRepeating = false,
            onSongEvents = {}
        )
    }
}