package com.example.musicplayer.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicplayer.data.player.MediaPlayBackService
import com.google.common.util.concurrent.ListenableFuture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityPlayerModule {

    @Provides
    @ActivityRetainedScoped
    fun getSessionToken(
        @ApplicationContext context: Context
    ): SessionToken {
        return SessionToken(
            context,
            ComponentName(context, MediaPlayBackService::class.java)
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun getListenableController(
        @ApplicationContext context: Context,
        session: SessionToken
    ): ListenableFuture<MediaController> {
        return MediaController.Builder(context, session)
            .buildAsync()
    }

}