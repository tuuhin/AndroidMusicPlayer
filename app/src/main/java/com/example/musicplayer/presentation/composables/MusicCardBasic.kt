package com.example.musicplayer.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.SongPreviewParameters
import com.example.musicplayer.presentation.util.formatTimeFromLong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicCardBasic(
    music: MusicResourceModel,
    modifier: Modifier = Modifier
) {
    val duration = formatTimeFromLong(time = music.duration)
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MusicAlbumArt(
                modifier = Modifier.weight(.2f),
                albumArt = music.albumArt,
                internalPadding = PaddingValues(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(.7f),
            ) {
                Text(
                    text = music.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                music.artist?.let { artist ->
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                music.album?.let { album ->
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
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = duration,
                        modifier = Modifier
                            .padding(4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MusicCardSimplePreview(
    @PreviewParameter(SongPreviewParameters::class)
    songs: MusicResourceModel
) {
    MusicCardBasic(music = songs)
}