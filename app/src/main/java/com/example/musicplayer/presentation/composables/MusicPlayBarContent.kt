package com.example.musicplayer.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.preview.FakeMusicModels
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun MusicPlayerBarContent(
    modifier: Modifier = Modifier,
    song: MusicResourceModel
) {
    Row(
        modifier = modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicAlbumArt(
            albumArt = song.albumArt,
            modifier = Modifier.size(48.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            elevation = 4.dp
        )
        Spacer(
            modifier = Modifier.width(8.dp)
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun MusicPlayerContentPreview() {
    MusicPlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            MusicPlayerBarContent(
                song = FakeMusicModels.fakeMusicResourceModel,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}