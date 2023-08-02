package com.example.musicplayer.presentation.util.states

import com.example.musicplayer.presentation.util.MusicSortOrder

data class MusicSortState(
    val isDialogOpen: Boolean = false,
    val sortOrder: MusicSortOrder = MusicSortOrder.CreatedAtDescending
)
sealed interface ChangeSortOrderEvents {
    object ToggleDialogState : ChangeSortOrderEvents
    data class OnOrderChanged(val order: MusicSortOrder) : ChangeSortOrderEvents
}