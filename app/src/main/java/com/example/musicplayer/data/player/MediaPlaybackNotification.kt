package com.example.musicplayer.data.player

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.example.musicplayer.R
import com.example.musicplayer.utils.AppConstants
import com.example.musicplayer.utils.NotificationConstants

@UnstableApi
class MediaPlaybackNotification(
    private val context: Context,
    private val listener: PlayerNotificationManager.NotificationListener
) {

    private val adapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return player.mediaMetadata.title
                ?: player.mediaMetadata.subtitle
                ?: ""
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? =null

        override fun getCurrentContentText(player: Player): CharSequence? {
            return player.mediaMetadata.artist
                ?: player.mediaMetadata.albumArtist
                ?: player.mediaMetadata.albumTitle
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            return player.mediaMetadata.artworkData?.let { array ->
                BitmapFactory.decodeByteArray(array, 0, array.size)
            }
        }

    }

    private val notificationManager by lazy {
        PlayerNotificationManager.Builder(
            context,
            AppConstants.PLAYBACK_NOTIFICATION_ID,
            NotificationConstants.NOTIFICATION_CHANNEL_ID
        )
            .setSmallIconResourceId(R.drawable.ic_music_note)
            .setNotificationListener(listener)
            .setMediaDescriptionAdapter(adapter)
            .build()
            .apply {
                setUseFastForwardAction(false)
                setUseRewindAction(false)
            }
    }

    fun showNotification(session: MediaSession) = notificationManager
        .setPlayer(session.player)


}