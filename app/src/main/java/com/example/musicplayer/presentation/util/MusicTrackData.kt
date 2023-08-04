package com.example.musicplayer.presentation.util

data class MusicTrackData(
    val current: Float = 0f,
    val duration: Float = 0f
) {
    val ratio: Float
        get() {
            val pos = 1 - (duration - current) / duration
            return if (pos.isNaN()) 0f
            else pos
        }
}
