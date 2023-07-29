package com.example.musicplayer.data.player

import android.app.Service
import android.util.Log
import android.widget.Toast
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaSessionService

class MediaPlayerEventListener(
    private val service: MediaSessionService
) : Player.Listener {
    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        if (state == Player.STATE_READY)
            service.stopForeground(Service.STOP_FOREGROUND_REMOVE)
        Log.d("LISTENER", state.toString())
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        error.printStackTrace()
        Log.e("PLAYER_LISTENER_ERROR",error.errorCodeName)
        Toast.makeText(service, "An unknown error occurred", Toast.LENGTH_LONG)
            .show()
    }
}