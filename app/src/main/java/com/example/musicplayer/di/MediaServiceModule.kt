package com.example.musicplayer.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaConstants
import androidx.media3.session.MediaSession
import com.example.musicplayer.data.player.MediaSessionCallbacks
import com.example.musicplayer.utils.AppConstants
import com.example.musicplayer.utils.MediaSessionConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object MediaServiceModule {

    @Provides
    @ServiceScoped
    fun provideAudioAttributes(): AudioAttributes {
        return AudioAttributes
            .Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }


    @Provides
    @ServiceScoped
    @UnstableApi
    fun getPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .setTrackSelector(DefaultTrackSelector(context))
            .build()
    }

    @Provides
    @ServiceScoped
    @UnstableApi
    fun getForwardingPLayer(
        player: ExoPlayer
    ): ForwardingPlayer {
        return object : ForwardingPlayer(player) {
            override fun getAvailableCommands(): Player.Commands {
                return super.getAvailableCommands()
                    .buildUpon()
                    .removeAll(
                        COMMAND_SEEK_TO_PREVIOUS,
                        COMMAND_SEEK_TO_NEXT,
                    )
                    .build()
            }
        }
    }

    @UnstableApi
    @Provides
    @ServiceScoped
    fun getMediaSessions(
        @ApplicationContext context: Context,
        player: ForwardingPlayer
    ): MediaSession {

        val pendingIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            ?.let { intent ->
                PendingIntent.getActivity(
                    context,
                    AppConstants.ACTIVITY_PENDING_REQUEST_ID,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
                )
            }
        val extras = Bundle().apply {
            putBoolean(MediaConstants.EXTRAS_KEY_SLOT_RESERVATION_SEEK_TO_PREV, true)
            putBoolean(MediaConstants.EXTRAS_KEY_SLOT_RESERVATION_SEEK_TO_NEXT, true)
        }

        return MediaSession.Builder(context, player)
            .apply {
                pendingIntent?.let { intent -> setSessionActivity(intent) }
            }
            .setExtras(extras)
            .setCallback(MediaSessionCallbacks())
            .setId(MediaSessionConstants.MEDIA_SESSION_TAG)
            .build()
    }

}