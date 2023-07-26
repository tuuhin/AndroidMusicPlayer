package com.example.musicplayer.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.composables.MusicCardBasic

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AudioFilesRoute(
    music: List<MusicResourceModel>
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Audio files") }) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            itemsIndexed(music, key = { _, item -> item.id }) { _, item ->
                MusicCardBasic(
                    resourceModel = item,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }

}