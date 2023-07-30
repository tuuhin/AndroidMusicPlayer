package com.example.musicplayer.domain.models

import android.graphics.Bitmap


data class MusicResourceModel(
    val uri: String,
    val title: String,
    val displayName: String,
    val id: Long,
    val artist: String? = null,
    val album: String? = null,
    val duration: Long,
    val albumArt: Bitmap? = null,
    val createdAt: Long
)
