package com.example.musicplayer.presentation.util

import com.example.musicplayer.domain.models.MusicResourceModel

data class SelectedSongState(
    val showBottomBar: Boolean = false,
    val isPlaying: Boolean = false,
    val current: MusicResourceModel? = null,
)
