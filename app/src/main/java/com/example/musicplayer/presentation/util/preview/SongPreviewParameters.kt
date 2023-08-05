package com.example.musicplayer.presentation.util.preview

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState

class SongPreviewParameters : CollectionPreviewParameterProvider<MusicResourceModel>(
    listOf(
        FakeMusicModels.fakeMusicResourceModel,
        FakeMusicModels.fakeMusicResourceModelExtraLongNames
    )
)

class CurrentSongPreviewParams : CollectionPreviewParameterProvider<CurrentSelectedSongState>(
    listOf(
        CurrentSelectedSongState(
            showBottomBar = true,
            current = FakeMusicModels.fakeMusicResourceModel
        ),
        CurrentSelectedSongState(
            showBottomBar = true,
            current = FakeMusicModels.fakeMusicResourceModelExtraLongNames,
            isRepeating = true
        ),
        CurrentSelectedSongState(
            showBottomBar = true,
            current = FakeMusicModels.fakeMusicResourceModel,
            isPlaying = true
        ),
        CurrentSelectedSongState(
            showBottomBar = true,
            current = FakeMusicModels.fakeMusicResourceModelExtraLongNames,
            isRepeating = true,
            isPlaying = true
        )
    )
)