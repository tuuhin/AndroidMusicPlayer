package com.example.musicplayer.presentation.routes

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.session.MediaController
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.domain.music.MusicFileReader
import com.example.musicplayer.presentation.util.MusicSortOrder
import com.example.musicplayer.presentation.util.SelectedSongState
import com.example.musicplayer.presentation.util.SortOrderChangeEvents
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioFilesViewModel @Inject constructor(
    private val reader: MusicFileReader,
    private val controller: ListenableFuture<MediaController>
) : ViewModel() {

    private val _files = MutableStateFlow<List<MusicResourceModel>>(emptyList())
    val audioFiles = _files.asStateFlow()

    private val _currentSelectedSong = MutableStateFlow(SelectedSongState())
    val currentSelectedSong = _currentSelectedSong.asStateFlow()

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
        viewModelScope.launch {
            val files = reader.readMusicFiles()
            _files.update { files }
        }
    }

    fun onSongSelect(model: MusicResourceModel?) {
        _currentSelectedSong.update {
            it.copy(
                isPlaying = true,
                current = model,
                showBottomBar = true
            )
        }
    }

    fun playSong(uri: Uri) {
//        controller.get().apply {
//            val mediaItem = MediaItem.Builder()
//                .setMediaId(uri.toString())
//                .setUri(uri)
//                .build()
//            prepare()
//            setMediaItem(mediaItem)
//            setMediaItem(mediaItem)
//            play()
//        }
    }

}