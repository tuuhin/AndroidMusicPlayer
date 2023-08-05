package com.example.musicplayer.presentation.routes

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import com.example.musicplayer.data.player.MediaControllerListener
import com.example.musicplayer.domain.models.MusicResourceModel
import com.example.musicplayer.presentation.util.MusicTrackData
import com.example.musicplayer.presentation.util.states.SongEvents
import com.example.musicplayer.presentation.util.states.CurrentSelectedSongState
import com.example.musicplayer.utils.MediaSessionConstants
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedSongPlayerViewModel @Inject constructor(
    private val playerController: ListenableFuture<MediaController>,
    private val playerListener: MediaControllerListener,
) : ViewModel() {

    private var mediaController = MutableStateFlow<MediaController?>(null)

    private val _currentSelectedSong = MutableStateFlow(CurrentSelectedSongState())


    val currentSelectedSong = combine(
        _currentSelectedSong,
        playerListener.isRepeatModeActive,
        playerListener.isPlaying
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

    val trackDataFlow = playerListener
        .playerDurationAsFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L), MusicTrackData()
        )

    init {
        viewModelScope.launch {
            val controller = playerController.await()
                .apply {
                    addListener(playerListener)
                }
            mediaController.update { controller }
        }
    }

    override fun onCleared() {
        mediaController.value?.release()
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
                mediaController.value?.let { controller ->
                    if (controller.playWhenReady && controller.playbackState != Player.STATE_ENDED)
                        pauseCurrentSong()
                    else playCurrentSong()
                }
            }

            SongEvents.ToggleIsRepeating -> {
                mediaController.value?.sendCustomCommand(
                    SessionCommand(
                        if (playerListener.isRepeatModeActive.value == Player.REPEAT_MODE_ONE)
                            MediaSessionConstants.REPEAT_CURRENT_SONG
                        else MediaSessionConstants.DO_NOT_REPEAT_CURRENT_SONG,
                        Bundle.EMPTY
                    ),
                    Bundle.EMPTY
                )
            }

            is SongEvents.OnTrackPositionChange -> {
                pauseCurrentSong()
                val seekPosition = (events.pos * trackDataFlow.value.duration).toLong()
                mediaController.value?.seekTo(seekPosition)
                playCurrentSong()
            }

            SongEvents.ForwardCurrentMedia -> mediaController.value?.sendCustomCommand(
                SessionCommand(MediaSessionConstants.FORWARD_BY_10_SEC, Bundle.EMPTY),
                Bundle.EMPTY
            )

            SongEvents.RewindCurrentMedia -> mediaController.value?.sendCustomCommand(
                SessionCommand(MediaSessionConstants.REWIND_BY_10_SEC, Bundle.EMPTY),
                Bundle.EMPTY
            )
        }
    }

    private fun playCurrentSong() = mediaController.value?.play()

    private fun pauseCurrentSong() = mediaController.value?.pause()

    fun onSongSelect(model: MusicResourceModel) {
        mediaController.value?.apply {
            prepare()
            setMediaItem(mediaItemBuilder(Uri.parse(model.uri)))
            play()
        }
        _currentSelectedSong.update { state ->
            state.copy(
                isPlaying = true,
                current = model,
                showBottomBar = true
            )
        }
    }
}