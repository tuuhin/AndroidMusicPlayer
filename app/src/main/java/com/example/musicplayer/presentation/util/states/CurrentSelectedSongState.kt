package com.example.musicplayer.presentation.util.states

import com.example.musicplayer.domain.models.MusicResourceModel

data class CurrentSelectedSongState(
    val showBottomBar: Boolean = false,
    val isPlaying: Boolean = false,
    val current: MusicResourceModel? = null,
    val isRepeating: Boolean = false,
)

sealed interface SongEvents {
    object ToggleIsPlaying : SongEvents

    object ToggleIsRepeating : SongEvents
}