package com.example.musicplayer.presentation.composables

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.FakeMusicModels
import com.example.musicplayer.presentation.util.formattedTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicCardBasic(
    resourceModel: MusicResourceModel,
    modifier: Modifier = Modifier
) {
    val duration = formattedTime(time = resourceModel.duration)
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        border = CardDefaults.outlinedCardBorder(enabled = true)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(.25f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    resourceModel.albumArt != null -> {
                        val bitmap by remember {
                            derivedStateOf {
                                BitmapFactory.decodeByteArray(
                                    resourceModel.albumArt,
                                    0,
                                    resourceModel.albumArt.size
                                )
                            }
                        }
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Music Note",
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_music_note),
                            contentDescription = "Music Note",
                            modifier = Modifier.padding(16.dp),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(.7f),
            ) {
                Text(
                    text = resourceModel.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                resourceModel.artist?.let { artist ->
                    Row(
                        modifier = Modifier.wrapContentWidth(),
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
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                resourceModel.album?.let { album ->
                    Row(
                        modifier = Modifier.wrapContentWidth(),
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
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MusicCardSimplePreview() {
    MusicCardBasic(resourceModel = FakeMusicModels.fakeMusicResourceModel)
}