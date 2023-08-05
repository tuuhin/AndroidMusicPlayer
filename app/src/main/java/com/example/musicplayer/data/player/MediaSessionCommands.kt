package com.example.musicplayer.data.player

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.SessionCommand
import com.example.musicplayer.R
import com.example.musicplayer.utils.MediaSessionConstants

object MediaSessionCommands {

    val rewindButton = getCustomButton(
        sessionCommand = SessionCommand(
            MediaSessionConstants.REWIND_BY_10_SEC,
            Bundle.EMPTY
        ),
        displayName = MediaSessionConstants.REWIND_BY_10_SEC,
        icon = R.drawable.ic_rewind_by_10
    )
    val forwardButton =
        getCustomButton(
            sessionCommand = SessionCommand(
                MediaSessionConstants.FORWARD_BY_10_SEC,
                Bundle.EMPTY
            ),
            displayName = MediaSessionConstants.FORWARD_BY_10_SEC,
            icon = R.drawable.ic_forward_by_10
        )
    val repeatButton =
        getCustomButton(
            sessionCommand = SessionCommand(
                MediaSessionConstants.REPEAT_CURRENT_SONG,
                Bundle.EMPTY
            ),
            displayName = MediaSessionConstants.REPEAT_CURRENT_SONG,
            icon = R.drawable.ic_repeat
        )

    val noRepeatButton = getCustomButton(
        sessionCommand = SessionCommand(
            MediaSessionConstants.DO_NOT_REPEAT_CURRENT_SONG,
            Bundle.EMPTY
        ),
        displayName = MediaSessionConstants.DO_NOT_REPEAT_CURRENT_SONG,
        icon = R.drawable.ic_no_repeat
    )

    val buttonsAsList = listOf(rewindButton, forwardButton, repeatButton, noRepeatButton)

    private fun getCustomButton(
        sessionCommand: SessionCommand,
        displayName: String,
        @DrawableRes icon: Int
    ): CommandButton {
        return CommandButton.Builder()
            .setDisplayName(displayName)
            .setSessionCommand(sessionCommand)
            .setIconResId(icon)
            .setEnabled(true)
            .build()
    }

    @UnstableApi
    fun playPauseButton(showPauseButton: Boolean): CommandButton {
        val extras = Bundle().apply {
            putInt(
                DefaultMediaNotificationProvider.COMMAND_KEY_COMPACT_VIEW_INDEX,
                C.INDEX_UNSET
            )
        }
        return CommandButton.Builder()
            .setPlayerCommand(Player.COMMAND_PLAY_PAUSE)
            .setIconResId(
                if (showPauseButton)
                    R.drawable.ic_pause
                else R.drawable.ic_play
            )
            .setDisplayName("Play/Pause")
            .setExtras(extras)
            .build()
    }

}