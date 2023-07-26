package com.example.musicplayer.domain.music

import com.example.musicplayer.domain.models.MusicResourceModel

interface MusicFileReader {

    suspend fun readMusicFiles(isAudioBooksToo: Boolean = false): List<MusicResourceModel>
}