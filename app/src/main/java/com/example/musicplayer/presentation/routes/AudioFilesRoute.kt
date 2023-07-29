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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.composables.MusicCardBasic
import com.example.musicplayer.presentation.composables.MusicPlayBackBottomBar
import com.example.musicplayer.presentation.util.FakeMusicModels

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun AudioFilesRoute(
    music: List<MusicResourceModel>,
    onItemSelect: (String) -> Unit,
    density: Density = LocalDensity.current
) {
    var selectSong by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Audio files") }) },
        bottomBar = {
            AnimatedVisibility(
                visible = selectSong,
                enter = slideInVertically(tween(400)) {
                    with(density) { 60.dp.roundToPx() }
                } + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically(tween(400)) {
                    with(density) { 60.dp.roundToPx() }
                } + fadeOut()
            ) {
                MusicPlayBackBottomBar()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            itemsIndexed(music,
                key = { _, item -> item.id }
            ) { _, item ->
                MusicCardBasic(
                    resourceModel = item,
                    modifier = Modifier
                        .clickable { onItemSelect(item.uri) }
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
        music = List(10) { FakeMusicModels.fakeMusicResourceModel },
        onItemSelect = {

        }
    )
}