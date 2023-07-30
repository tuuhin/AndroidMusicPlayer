package com.example.musicplayer.presentation.util

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.example.musicplayer.domain.models.MusicResourceModel

class SongPreviewParameters : CollectionPreviewParameterProvider<MusicResourceModel>(
    listOf(
        FakeMusicModels.fakeMusicResourceModel,
        FakeMusicModels.fakeMusicResourceModelExtraLongNames
    )
)