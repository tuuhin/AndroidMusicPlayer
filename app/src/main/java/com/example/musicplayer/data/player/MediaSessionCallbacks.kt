package com.example.musicplayer.data.player

import android.os.Bundle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.example.musicplayer.utils.MediaSessionConstants
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

@UnstableApi
class MediaSessionCallbacks : MediaSession.Callback {

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {

        val result = super.onConnect(session, controller)
        val sessionCommands = result.availableSessionCommands
            .buildUpon()
            .apply {
                MediaSessionCommands.buttonsAsList.forEach { button ->
                    button.sessionCommand?.let { add(it) }
                }
            }
            .build()

        return MediaSession.ConnectionResult.accept(sessionCommands, result.availablePlayerCommands)
    }

    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        if (controller.controllerVersion != 0) {
            session.setCustomLayout(
                ImmutableList.of(
                    MediaSessionCommands.rewindButton,
                    MediaSessionCommands.forwardButton,
                    MediaSessionCommands.noRepeatButton
                )
            )
        }
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        when (customCommand.customAction) {
            MediaSessionConstants.DO_NOT_REPEAT_CURRENT_SONG -> {
                session.player.repeatMode = Player.REPEAT_MODE_ONE
                session.setCustomLayout(
                    ImmutableList.of(
                        MediaSessionCommands.rewindButton,
                        MediaSessionCommands.forwardButton,
                        MediaSessionCommands.repeatButton
                    )
                )
            }

            MediaSessionConstants.REPEAT_CURRENT_SONG -> {
                session.player.repeatMode = Player.REPEAT_MODE_OFF
                session.setCustomLayout(
                    ImmutableList.of(
                        MediaSessionCommands.rewindButton,
                        MediaSessionCommands.forwardButton,
                        MediaSessionCommands.noRepeatButton
                    )
                )
            }

            MediaSessionConstants.FORWARD_BY_10_SEC -> {
                val currentPos = session.player.currentPosition
                session.player.seekTo(currentPos + 10000)
            }

            MediaSessionConstants.REWIND_BY_10_SEC -> {
                val currentPos = session.player.currentPosition
                session.player.seekTo(currentPos - 10000)
            }
        }
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }
}