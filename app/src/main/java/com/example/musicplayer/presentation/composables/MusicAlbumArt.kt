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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun MusicAlbumArt(
    modifier: Modifier = Modifier,
    albumArt: Bitmap? = null,
    internalPadding: PaddingValues = PaddingValues(8.dp),
    elevation: Dp = 2.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    shape: Shape = MaterialTheme.shapes.medium
) {
    Surface(
        shadowElevation = elevation,
        shape = shape,
        modifier = modifier.aspectRatio(1f)
    ) {
        albumArt?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Album Image",
                contentScale = ContentScale.FillBounds
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
    MusicPlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.medium
        ) {
            MusicAlbumArt(
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}