package com.example.musicplayer.presentation.routes

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.musicplayer.data.player.MediaControllerListener
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedSongPlayerViewModel @Inject constructor(
    private val listenableController: ListenableFuture<MediaController>,
    controllerListener: MediaControllerListener,
) : ViewModel() {

    private var controller = MutableStateFlow<MediaController?>(null)

    private val _currentSelectedSong = MutableStateFlow(CurrentSelectedSongState())

    val currentSelectedSong = combine(
        _currentSelectedSong,
        controllerListener.isRepeatModeActive,
        controllerListener.isPlaying
    ) { current, repeat, playing ->
        current.copy(
            isPlaying = playing,
            isRepeating = repeat == Player.REPEAT_MODE_ONE
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CurrentSelectedSongState()
    )

    private val _totalDuration = MutableStateFlow(0L)
    val totalDuration = _totalDuration.asStateFlow()

    val currentDuration = controllerListener
        .playerDurationAsFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L), 0L
        )

    init {
        viewModelScope.launch {
            controller.update { listenableController.await() }
        }
    }

    override fun onCleared() {
        controller.value?.release()
        super.onCleared()
    }

    private fun mediaItemBuilder(uri: Uri): MediaItem =
        MediaItem.Builder()
            .setMediaId(uri.toString())
            .setRequestMetadata(
                MediaItem.RequestMetadata.Builder()
                    .setMediaUri(uri)
                    .build()
            )
            .setUri(uri)
            .build()

    fun onPlaySongEvents(events: SongEvents) {
        when (events) {
            SongEvents.ToggleIsPlaying -> {
                if (!_currentSelectedSong.value.isPlaying) pause() else play()

                _currentSelectedSong.update { state ->
                    state.copy(isPlaying = !state.isPlaying)
                }
            }

            SongEvents.ToggleIsRepeating -> {
                _currentSelectedSong.update { state ->
                    state.copy(isRepeating = !state.isRepeating)
                }
                controller.value?.apply {
                    repeatMode = if (_currentSelectedSong.value.isRepeating)
                        Player.REPEAT_MODE_ONE
                    else Player.REPEAT_MODE_OFF
                }
            }
        }
    }

    private fun play() = controller.value?.play()

    private fun pause() = controller.value?.pause()

    fun onSongSelect(model: MusicResourceModel) {
        _currentSelectedSong.update {
            it.copy(
                isPlaying = true,
                current = model,
                showBottomBar = true
            )
        }
        controller.value?.apply {
            prepare()
            setMediaItem(mediaItemBuilder(Uri.parse(model.uri)))
            play()
        }
    }

}