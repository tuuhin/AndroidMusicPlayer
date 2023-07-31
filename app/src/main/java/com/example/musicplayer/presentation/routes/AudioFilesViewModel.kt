package com.example.musicplayer.presentation.routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.domain.music.MusicFileReader
import com.example.musicplayer.presentation.util.MusicSortOrder
import com.example.musicplayer.presentation.util.SortOrderChangeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioFilesViewModel @Inject constructor(
    private val reader: MusicFileReader,
) : ViewModel() {

    private val _files = MutableStateFlow<List<MusicResourceModel>>(emptyList())
    val audioFiles = _files.asStateFlow()

    private val _sortOrder = MutableStateFlow(MusicSortOrder.CreatedAtDescending)
    val sortOrder = _sortOrder.asStateFlow()

    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen = _isDialogOpen.asStateFlow()

    init {
        loadFiles()
    }


    fun onSortEvents(events: SortOrderChangeEvents) {
        when (events) {
            is SortOrderChangeEvents.OnOrderChanged -> onSortOrderChanged(events.order)
            SortOrderChangeEvents.ToggleChangeSortOrderDialog -> onToggleSortDialog()
        }
    }

    private fun onToggleSortDialog() = _isDialogOpen.update { !_isDialogOpen.value }

    private fun onSortOrderChanged(order: MusicSortOrder) {
        _sortOrder.update { order }
        when (order) {
            MusicSortOrder.AscendingByDuration -> _files.update { files ->

                files.sortedBy { it.duration }
            }

            MusicSortOrder.CreatedAtAscending -> _files.update { files ->
                files.sortedBy { it.createdAt }
            }

            MusicSortOrder.CreatedAtDescending -> _files.update { files ->
                files.sortedByDescending { it.createdAt }
            }

            MusicSortOrder.DescendingByDuration -> _files.update { files ->
                files.sortedByDescending { it.duration }
            }
        }
    }

    private fun loadFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            val files = reader.readMusicFiles()
            _files.update { files }
        }
    }

}