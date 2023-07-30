package com.example.musicplayer.presentation.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun MusicAlbumArt(
    modifier: Modifier = Modifier,
    albumArt: Bitmap? = null,
    internalPadding: PaddingValues = PaddingValues(8.dp),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        albumArt?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Album Image",
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
            )
        } ?: Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(containerColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = "Music Note",
                modifier = Modifier.padding(internalPadding),
                colorFilter = ColorFilter.tint(color = contentColor)
            )
        }
    }
}


@Preview
@Composable
fun MusicAlbumArtPreview() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        MusicAlbumArt(modifier = Modifier.padding(4.dp))
    }
}