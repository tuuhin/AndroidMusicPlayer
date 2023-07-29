package com.example.musicplayer.di

import android.app.PendingIntent
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.example.musicplayer.utils.AppConstants
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
    fun getPlayer(
        @ApplicationContext context: Context
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()
    }

    @Provides
    @ServiceScoped
    fun getMediaSessions(
        @ApplicationContext context: Context,
        player: ExoPlayer
    ): MediaSession {

        val pendingIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.let { intent ->
                PendingIntent.getActivity(
                    context,
                    AppConstants.ACTIVITY_PENDING_REQUEST_ID,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
                )
            }

        return MediaSession.Builder(context, player)
            .apply {
                pendingIntent?.let { intent -> setSessionActivity(intent) }
            }
            .build()
    }

    @ServiceScoped
    @Provides
    @UnstableApi
    fun provideDataSourceFactory(
        @ApplicationContext context: Context
    ): DataSource = DefaultDataSource.Factory(context).createDataSource()

}