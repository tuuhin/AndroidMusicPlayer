package com.example.musicplayer.data.player

import android.app.Service
import android.media.session.PlaybackState
import android.widget.Toast
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@UnstableApi
@AndroidEntryPoint
class MediaPlayBackService : MediaSessionService() {

    @Inject
    lateinit var session: MediaSession

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private val playerListener = object : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            if (playbackState == PlaybackState.STATE_ERROR) {
                stopForeground(Service.STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Toast.makeText(
                this@MediaPlayBackService,
                error.errorCodeName,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @UnstableApi
    override fun onCreate() {
        super.onCreate()

        exoPlayer.addListener(playerListener)

        setMediaNotificationProvider(
            MediaSessionCommands.defaultNotificationProvider(this)
        )
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return session
    }

    @UnstableApi
    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        super.onUpdateNotification(session, startInForegroundRequired)
    }


    override fun onDestroy() {
        session.apply {
            player.release()
            release()
        }
        super.onDestroy()
    }

}