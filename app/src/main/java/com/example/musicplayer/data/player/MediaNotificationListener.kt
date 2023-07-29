package com.example.musicplayer.data.player

import android.app.Notification
import android.app.Service
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import com.example.musicplayer.utils.AppConstants

@UnstableApi
class MediaNotificationListener(
    private val service: MediaPlayBackService
) : PlayerNotificationManager.NotificationListener {
    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        service.apply {
            stopForeground(Service.STOP_FOREGROUND_DETACH)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int, notification: Notification, ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        service.apply {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
                startForeground(AppConstants.PLAYBACK_NOTIFICATION_ID, notification)
                isForegroundService = true
            }
        }
    }
}