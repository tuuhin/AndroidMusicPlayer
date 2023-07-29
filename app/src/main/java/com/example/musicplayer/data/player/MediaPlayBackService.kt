package com.example.musicplayer.data.player

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MediaPlayBackService : MediaSessionService() {

    @Inject
    lateinit var session: MediaSession

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private lateinit var playbackNotification: MediaPlaybackNotification

    var isForegroundService: Boolean = false

    @UnstableApi
    override fun onCreate() {
        super.onCreate()

        playbackNotification = MediaPlaybackNotification(
            context = this,
            listener = MediaNotificationListener(this),
        )
        exoPlayer.addListener(MediaPlayerEventListener(this))
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = session

    @UnstableApi
    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        isForegroundService = startInForegroundRequired
       super.onUpdateNotification(session, startInForegroundRequired)
         playbackNotification.showNotification(session)
    }

    override fun onDestroy() {
        session.apply {
            player.release()
            release()
        }
        super.onDestroy()
    }

}