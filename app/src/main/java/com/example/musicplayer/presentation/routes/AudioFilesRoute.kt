package com.example.musicplayer.presentation.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.composables.MusicCardBasic
import com.example.musicplayer.presentation.composables.MusicPlayerBar
import com.example.musicplayer.presentation.composables.MusicSortOptions
import com.example.musicplayer.presentation.util.FakeMusicModels
import com.example.musicplayer.presentation.util.MusicSortOrder
import com.example.musicplayer.presentation.util.SelectedSongState
import com.example.musicplayer.presentation.util.SortOrderChangeEvents

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun AudioFilesRoute(
    isDialogOpen: Boolean,
    sortOrder: MusicSortOrder,
    onSortEvents: (SortOrderChangeEvents) -> Unit,
    currentSelectedSong: SelectedSongState,
    music: List<MusicResourceModel>,
    onItemSelect: (MusicResourceModel) -> Unit,
    density: Density = LocalDensity.current
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
                        onClick = { onSortEvents(SortOrderChangeEvents.ToggleChangeSortOrderDialog) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Sort Order"
                        )
                    }
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = currentSelectedSong.showBottomBar,
                enter = slideInVertically(tween(400)) {
                    with(density) { 60.dp.roundToPx() }
                } + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically(tween(400)) {
                    with(density) { 60.dp.roundToPx() }
                } + fadeOut()
            ) {
                MusicPlayerBar(
                    currentSelectedSong = currentSelectedSong,
                    onPlayPause = {},
                )
            }
        }
    ) { paddingValues ->

        if (isDialogOpen)
            MusicSortOptions(
                sortOrder = sortOrder,
                onSortOrderChange = { order ->
                    onSortEvents(SortOrderChangeEvents.OnOrderChanged(order))
                },
                onDismissRequest = {
                    onSortEvents(SortOrderChangeEvents.ToggleChangeSortOrderDialog)
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

@Preview
@Composable
fun AudioFilesRoutePreview() {
    AudioFilesRoute(
        currentSelectedSong = SelectedSongState(),
        music = List(10) {
            FakeMusicModels.fakeMusicResourceModel.copy(id = it.toLong())
        },
        onItemSelect = {},
        sortOrder = MusicSortOrder.CreatedAtDescending,
        isDialogOpen = false,
        onSortEvents = {}
    )

}