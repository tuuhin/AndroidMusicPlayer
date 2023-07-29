package com.example.musicplayer.di

import android.content.Context
import com.example.musicplayer.data.music.MusicFileReaderImpl
import com.example.musicplayer.domain.music.MusicFileReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun musicFileReader(
        @ApplicationContext context: Context
    ): MusicFileReader {
        return MusicFileReaderImpl(context)
    }
}