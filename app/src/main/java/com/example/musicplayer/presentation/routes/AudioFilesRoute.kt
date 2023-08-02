package com.example.musicplayer.presentation.routes

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.composables.MusicCardBasic
import com.example.musicplayer.presentation.composables.MusicPlayerBar
import com.example.musicplayer.presentation.composables.MusicSortOptions
import com.example.musicplayer.presentation.util.preview.FakeMusicModels
import com.example.musicplayer.presentation.util.states.MusicSortState
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.example.musicplayer.presentation.util.states.ChangeSortOrderEvents
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun AudioFilesRoute(
    sortState: MusicSortState,
    onSortEvents: (ChangeSortOrderEvents) -> Unit,
    currentSong: CurrentSelectedSongState,
    music: List<MusicResourceModel>,
    onItemSelect: (MusicResourceModel) -> Unit,
    onSongEvents: (SongEvents) -> Unit,
    onDetailsRoute: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Audio Files",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onSortEvents(ChangeSortOrderEvents.ToggleDialogState) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort_order),
                            contentDescription = "Sort Order",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            MusicPlayerBar(
                currentSong = currentSong,
                onSongEvents = onSongEvents,
                modifier = Modifier.clickable {
                    currentSong.current?.uri?.let { uri -> onDetailsRoute(uri) }
                }
            )
        }
    ) { paddingValues ->

        MusicSortOptions(
            sortState = sortState,
            onSortOrderChange = { order ->
                onSortEvents(ChangeSortOrderEvents.OnOrderChanged(order))
            },
            onDismissRequest = {
                onSortEvents(ChangeSortOrderEvents.ToggleDialogState)
            }
        )

        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            itemsIndexed(
                music,
                key = { _, item -> item.id }
            ) { _, item ->
                MusicCardBasic(
                    music = item,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onItemSelect(item) }
                        .animateItemPlacement()
                )
            }
        }
    }
}


class CurrentSongPreviewParams : CollectionPreviewParameterProvider<CurrentSelectedSongState>(
    listOf(
        CurrentSelectedSongState(),
        CurrentSelectedSongState(
            showBottomBar = true,
            current = FakeMusicModels.fakeMusicResourceModel
        )
    )
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun AudioFilesRoutePreview(
    @PreviewParameter(CurrentSongPreviewParams::class)
    currentSong: CurrentSelectedSongState
) {
    MusicPlayerTheme {
        AudioFilesRoute(
            currentSong = currentSong,
            music = List(10) {
                FakeMusicModels.fakeMusicResourceModel.copy(id = it.toLong())
            },
            onItemSelect = {},
            sortState = MusicSortState(),
            onSortEvents = {},
            onSongEvents = {},
            onDetailsRoute = {}
        )
    }
}