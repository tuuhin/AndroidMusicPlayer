package com.example.musicplayer.presentation.routes

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.SelectSongEvents
import com.example.musicplayer.presentation.util.SelectedSongState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SongPlayerSharedViewModel @Inject constructor(
    private val listenableController: ListenableFuture<MediaController>
) : ViewModel() {

    private var controller by mutableStateOf<MediaController?>(null)

    private val _currentSelectedSong = MutableStateFlow(SelectedSongState())
    val currentSelectedSong = _currentSelectedSong.asStateFlow()


    private val _listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _currentSelectedSong.update { it.copy(isPlaying = isPlaying) }
        }

    }

    init {
        listenableController.addListener(
            {
                controller = listenableController.get()
                controller?.addListener(_listener)
                Log.d("CONTROLLER_SET", controller.toString())
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onCleared() {
        controller?.release()
        super.onCleared()
    }

    private fun mediaItemBuilder(uri: Uri): MediaItem =
        MediaItem.Builder()
            .setMediaId(uri.toString())
            .setUri(uri)
            .build()

    fun onPlaySongEvents(events: SelectSongEvents) {
        when (events) {
            SelectSongEvents.ToggleIsPlaying -> {
                _currentSelectedSong.update {
                    it.copy(isPlaying = !it.isPlaying)
                }
                if (!_currentSelectedSong.value.isPlaying) {
                    pause()
                } else {
                    play()
                }
            }
        }
    }


    private fun play() = controller?.play()

    private fun pause() = controller?.pause()

    fun onSongSelect(model: MusicResourceModel) {
        _currentSelectedSong.update {
            it.copy(
                isPlaying = true,
                current = model,
                showBottomBar = true
            )
        }
        controller?.apply {
            prepare()
            setMediaItem(mediaItemBuilder(Uri.parse(model.uri)))
            play()
        }

    }

}