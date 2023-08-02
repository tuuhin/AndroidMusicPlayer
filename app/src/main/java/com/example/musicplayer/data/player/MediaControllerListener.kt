package com.example.musicplayer.data.player

import android.util.Log
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import kotlin.time.Duration.Companion.seconds

class MediaControllerListener(
    private val futureController: ListenableFuture<MediaController>
) : Player.Listener {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    val isRepeatModeActive = _repeatMode.asStateFlow()

    fun playerDurationAsFlow(): Flow<Long> {
        return flow {
            val controller = futureController.await()
            val checkIfDurationNeeded = controller.playbackState != Player.STATE_ENDED
            Log.d("CHECK", checkIfDurationNeeded.toString())
            while (checkIfDurationNeeded) {
                delay(1.seconds)
                val duration = controller.totalBufferedDuration
                Log.d(
                    "DURATION",
                    "${controller.totalBufferedDuration} ${controller.contentDuration}"
                )
                emit(duration)
            }
        }.cancellable()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        _isPlaying.update { isPlaying }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        _repeatMode.update { repeatMode }
    }

}