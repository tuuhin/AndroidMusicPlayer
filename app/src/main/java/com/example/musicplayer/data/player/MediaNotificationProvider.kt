package com.example.musicplayer.data.player

import android.content.Context
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import com.example.musicplayer.R
import com.example.musicplayer.utils.AppConstants
import com.example.musicplayer.utils.NotificationConstants
import com.google.common.collect.ImmutableList

@UnstableApi
class MediaNotificationProvider(
    context: Context
) : DefaultMediaNotificationProvider(
    context, NotificationIdProvider { AppConstants.PLAYBACK_NOTIFICATION_ID },
    NotificationConstants.NOTIFICATION_CHANNEL_ID,
    R.string.notification_channel_name
) {
    override fun getNotificationContentTitle(metadata: MediaMetadata): CharSequence? {
        return metadata.title
    }

    override fun getNotificationContentText(metadata: MediaMetadata): CharSequence? {
        return metadata.artist ?: metadata.albumArtist
    }

    override fun getMediaButtons(
        session: MediaSession,
        playerCommands: Player.Commands,
        customLayout: ImmutableList<CommandButton>,
        showPauseButton: Boolean
    ): ImmutableList<CommandButton> {
        super.getMediaButtons(session, playerCommands, customLayout, showPauseButton)
        val rewindButton = customLayout.first()
        val restButtons = customLayout.drop(1)
        val arrangement = ImmutableList.Builder<CommandButton>()
            .add(rewindButton)
            .apply {
                if (playerCommands.contains(Player.COMMAND_PLAY_PAUSE)) {
                    add(MediaSessionCommands.playPauseButton(showPauseButton))
                }
                restButtons.forEach { commandButton -> add(commandButton) }
            }
            .build()
        return arrangement

    }
}